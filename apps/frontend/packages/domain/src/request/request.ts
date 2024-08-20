import Cookies from 'js-cookie';
import type { $Fetch, FetchOptions, FetchRequest, FetchResponse } from 'ofetch';
import { ofetch } from 'ofetch';
import type AccessToken from "~/authentication/domain/AccessToken.ts";

const XSRF_TOKEN_COOKIE = 'XSRF-TOKEN';
const CSRF_HEADER = 'X-XSRF-TOKEN';
const LANGUAGE_COOKIE = 'language';
const REFRESH_TOKEN_COOKIE = 'refreshToken'

const setAuthorizationHeader = (options: FetchOptions, token: string) => {
  options.headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`,
  };
};

const setLanguageHeader = (options: FetchOptions, language: string) => {
  options.headers = {
    ...options.headers,
    'Accept-Language': language,
  };
};

const handleTokenRefresh = async (baseURL: string = process.env.API_URL || ''): Promise<AccessToken | null> => {
  try {
    const { accessToken } = await ofetch('/refresh-token', {
      baseURL: `${baseURL}/api`,
      method: 'POST',
      credentials: 'include',
    });
    return accessToken;
  } catch (error) {
    console.error('Failed to refresh token:', error);
    return null;
  }
};
const fetchCsrfToken = async (baseURL: string): Promise<string | null> => {
  try {
    const response = await ofetch<string>('/health-check', {
      baseURL: `${baseURL}/api`,
      method: 'GET',
      credentials: 'include',
    });

    if (response !== 'OK') {
      throw new Error('Failed to fetch CSRF token');
    }

    const xsrfToken = Cookies.get(XSRF_TOKEN_COOKIE);
    return xsrfToken || null;
  } catch (error) {
    console.error('Failed to fetch CSRF token:', error);
    return null;
  }
};

const createFetcher = (baseURL: string = process.env.API_URL || '') => {
  const config: FetchOptions = {
    baseURL: `${baseURL}/api`,
    credentials: 'include',
    async onRequest({ options }) {
      const language = Cookies.get(LANGUAGE_COOKIE);
      const xrsfToken = Cookies.get(XSRF_TOKEN_COOKIE) || await fetchCsrfToken(baseURL);

      // if (encodedAccessToken) {
      //   const tokenData = parseAccessToken(encodedAccessToken);
      //   if (tokenData?.token) {
      //     setAuthorizationHeader(options, tokenData.token);
      //   }
      // }

      if (language) {
        setLanguageHeader(options, language);
      }
      if (xrsfToken) {
        options.headers = {
          ...options.headers,
          [CSRF_HEADER]: xrsfToken,
        };
      }
    },
    // biome-ignore lint/correctness/noUnusedVariables: This is a callback function
    async onResponse({ request, options, response }) {
      if (response.status === 401 /*&& Cookies.get(authCookieName)*/) {
        const newAccessToken = await handleTokenRefresh(baseURL);
        if (newAccessToken) {
          response = await fetcher.raw(response.url, {
            ...options,
            headers: {
              ...options.headers,
              Authorization: `Bearer ${newAccessToken.token}`,
            },
          });
        }
      }
    },
  };

  const fetcher: $Fetch = ofetch.create(config);

  return async <T>(
    request: FetchRequest,
    options?: FetchOptions
  ): Promise<FetchResponse<T>> => {
    try {
      const response = await fetcher.raw(request, options);
      return response as FetchResponse<T>;
    // biome-ignore lint/suspicious/noExplicitAny:  This is a catch-all error handler
    } catch (error: any) {
      if (error.response?.status === 401 && Cookies.get(REFRESH_TOKEN_COOKIE)) {
        const newAccessToken = await handleTokenRefresh(baseURL);
        if (newAccessToken) {
          setAuthorizationHeader(options!, newAccessToken.token);
          const response = await fetcher.raw(request, options);
          return response as FetchResponse<T>;
        }
      }
      throw error;
    }
  };
};

export default createFetcher;

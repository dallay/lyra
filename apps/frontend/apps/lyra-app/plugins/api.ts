import { defineNuxtPlugin, useCookie, useRuntimeConfig } from '#imports';
import type { FetchResponse, ResponseType } from 'ofetch';
import FormModule from '~/repository/modules/form.module';
import AuthModule from '~/repository/modules/auth.module';
import CommonModule from '~/repository/modules/common.module';
import type { AccessToken } from '@lyra/api-services';

/**
 * Interface representing the API instance with different modules.
 */
export interface IApiInstance {
  form: FormModule;
  auth: AuthModule;
  common: CommonModule;
}

/**
 * Utility function to create a request with updated headers.
 * @param {Request | string} request - The original request or URL string.
 * @param {Record<string, string>} headers - The headers to be added or updated.
 * @returns {Request} - The updated request with new headers.
 */
function createRequestWithHeaders(request: Request | string, headers: Record<string, string>): Request {
  if (typeof request === 'string') {
    return new Request(request, { headers });
  }
   Object.assign(request.headers, headers);
  return request;
}

/**
 * Utility function to retry a request with a new access token.
 * @param {Request | string} request - The original request or URL string.
 * @param {AccessToken} newAccessToken - The new access token.
 * @returns {Promise<FetchResponse<any> & FetchResponse<ResponseType>>} - The response of the retried request.
 */

// biome-ignore lint/suspicious/noExplicitAny: The response type is not known
async  function retryRequest(request: Request | string, newAccessToken: AccessToken): Promise<FetchResponse<any> & FetchResponse<ResponseType>> {
  const headers = { Authorization: `Bearer ${newAccessToken}` };
  const updatedRequest = createRequestWithHeaders(request, headers);
  // biome-ignore lint/correctness/noUndeclaredVariables: fetch is a global function in nuxt
  return $fetch(updatedRequest);
}

/**
 * Nuxt plugin to set up API modules and handle request/response logic.
 */
export default defineNuxtPlugin(async (_) => {
  const config = useRuntimeConfig();
  const baseUrlProxyServer: string = config.public.backendUrl;

  // biome-ignore lint/correctness/noUndeclaredVariables: $fetch is a global function in nuxt
  const apiFetcher = $fetch.create({
    baseURL: baseUrlProxyServer,
    // biome-ignore lint/correctness/noUnusedVariables: Biome needs support for Nuxt.
    onRequest({ request, response }) {
      const language = useCookie('language').value;
      if (language) {
        const headers = { 'Accept-Language': language };
        request = createRequestWithHeaders(request, headers);
      }
    },
    onResponse: async ({ request, response }) => {
      if (response.status === 401 || response.status === 403) {
        try {
          const newAccessToken = await authModule.refreshToken();
          if (newAccessToken) {
            response = await retryRequest(request, newAccessToken);
          }
        } catch (err) {
          console.error('Error refreshing token:', err);
        }
      }
    },
  });

  const formModule = new FormModule(apiFetcher);
  const authModule = new AuthModule(apiFetcher);
  const commonModule = new CommonModule(apiFetcher);

  const modules: IApiInstance = {
    form: formModule,
    auth: authModule,
    common: commonModule,
  };

  if (import.meta.client) {
    try {
      await modules.common.csrfToken();
      const data = await modules.auth.refreshToken();
      if (data.token) {
        formModule.setAccessToken(data.tokenType);
      }
    } catch (err) {
      console.error('Error occurred during client initialization:', err);
    }
  }

  return {
    provide: {
      api: modules,
    },
  };
});

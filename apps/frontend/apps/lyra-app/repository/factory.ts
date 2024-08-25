import type { $Fetch, NitroFetchOptions } from 'nitropack';
import { useCookie } from '#imports';

interface IHttpFactory {
	method:
		| 'GET'
		| 'HEAD'
		| 'PATCH'
		| 'POST'
		| 'PUT'
		| 'DELETE'
		| 'CONNECT'
		| 'OPTIONS'
		| 'TRACE'
		| 'get'
		| 'head'
		| 'patch'
		| 'post'
		| 'put'
		| 'delete'
		| 'connect'
		| 'options'
		| 'trace';
	url: string;
	fetchOptions?: NitroFetchOptions<'json'>;
	body?: object;
}

export const XSRF_TOKEN_COOKIE = 'XSRF-TOKEN';
const XSRF_TOKEN_HEADER = 'X-XSRF-TOKEN';
export const ACCEPT_HEADER = 'application/vnd.api.v1+json';

class HttpFactory {
	private readonly $fetch: $Fetch;

	constructor(fetch: $Fetch) {
		this.$fetch = fetch;
	}

	async call<T>({ method, url, fetchOptions, body }: IHttpFactory): Promise<T> {
    const headers = await this.buildHeaders();
		return this.$fetch<T>(url, {
			method,
			body,
			headers,
			credentials: 'include',
			...fetchOptions,
		});
	}

  private async buildHeaders() {
		const headers: HeadersInit = {
			Accept: ACCEPT_HEADER,
		};
		const xsrfToken = useCookie(XSRF_TOKEN_COOKIE).value;
		if (xsrfToken) {
			headers[XSRF_TOKEN_HEADER] = xsrfToken;
		}
		const language = useCookie('language').value;
		if (language) {
			headers['Accept-Language'] = language;
		}
		return headers;
	}
}
export default HttpFactory;

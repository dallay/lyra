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
const CSRF_HEADER = 'X-XSRF-TOKEN';

class HttpFactory {
	private readonly $fetch: $Fetch;

	constructor(fetch: $Fetch) {
		this.$fetch = fetch;
	}

	async call<T>({ method, url, fetchOptions, body }: IHttpFactory): Promise<T> {
		return this.$fetch<T>(url, {
			method,
			body,
			headers: await this.buildHeaders(),
			credentials: 'include',
			...fetchOptions,
		});
	}

	private async buildHeaders() {
		const headers: HeadersInit = {
			'Content-Type': 'application/vnd.api.v1+json',
			Accept: 'application/vnd.api.v1+json',
		};
		const xsrfToken = useCookie(XSRF_TOKEN_COOKIE).value;
		if (xsrfToken) {
			headers[CSRF_HEADER] = xsrfToken;
		}
		const language = useCookie('language').value;
		if (language) {
			headers['Accept-Language'] = language;
		}
		return headers;
	}
}
export default HttpFactory;

import type { FetchOptions, FetchRequest, FetchResponse } from 'ofetch';
import { ofetch } from 'ofetch';

const isBrowser = typeof window !== 'undefined';
const storage = isBrowser
	? window.localStorage
	: {
			getItem: () => null,
			setItem: () => null,
		};

const config: FetchOptions = {
	baseURL: `${process.env.API_URL}/api`,
	async onRequest({ options }) {
		const accessToken = storage.getItem('accessToken');
		const language = storage.getItem('language');

		if (accessToken) {
			options.headers = {
				...options.headers,
				Authorization: `Bearer ${accessToken}`,
			};
		}

		if (language) {
			options.headers = {
				...options.headers,
				'Accept-Language': language,
			};
		}
	},
	async onResponse({ response }) {
		if (response.status === 401 && storage.getItem('refreshToken')) {
			const { accessToken } = await ofetch('/auth/token', {
				baseURL: `${process.env.API_URL}/api`,
				method: 'POST',
				body: {
					accessToken: storage.getItem('accessToken'),
					refreshToken: storage.getItem('refreshToken'),
				},
			});

			storage.setItem('accessToken', accessToken);
		}
	},
};

const fetcher = ofetch.create(config);

export default async <T>(request: FetchRequest, options?: FetchOptions) => {
	try {
		const response = await fetcher.raw(request, options);
		return response as FetchResponse<T>;
	} catch (error: any) {
		if (error.response?.status === 401 && storage.getItem('refreshToken')) {
			const response = await fetcher.raw(request, options);
			return response as FetchResponse<T>;
		}

		return error.response as FetchResponse<T>;
	}
};

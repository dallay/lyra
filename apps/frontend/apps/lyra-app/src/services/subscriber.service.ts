import { BACKEND_API_URL } from '@/constants';
import type { Sort } from '@/models/Sort.ts';
import type { PageResponse } from '@/models/PageResponse.ts';
import type { Subscriber } from '@/models/Subscriber.ts';

class SubscriberService {
	private static instance: SubscriberService;

	private constructor() {}

	public static getInstance(): SubscriberService {
		if (!SubscriberService.instance) {
			SubscriberService.instance = new SubscriberService();
		}
		return SubscriberService.instance;
	}

	public async getSubscribers(
		criteria?: { search?: string; filter?: string },
		sort?: Sort,
		size: number = 10,
		cursor?: string
	): Promise<PageResponse<Subscriber>> {
		const url = new URL(`${BACKEND_API_URL}newsletter/subscribers`);
		const params = new URLSearchParams();
		params.append('search', criteria?.search || '');
		this.filterParamQuery(criteria?.filter, params);
		this.sortParamQuery(sort, params);
		params.append('size', size.toString());

		if (cursor) {
			params.append('cursor', cursor);
		}

		url.search = params.toString();

		const response = await fetch(url);
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		return await response.json();
	}

	private filterParamQuery(filter: string | undefined, params: URLSearchParams) {
		if (filter) {
			const filters = filter.split('&');
			filters.forEach((f) => {
				const [key, value] = f.split('=');
				params.append(`filter[${key}]`, value);
			});
		}
	}
	private sortParamQuery(sort: Sort | undefined, params: URLSearchParams) {
		if (sort) {
			const sortQuery = sort.toQueryString();
			const [key, value] = sortQuery.split('=');
			params.append(key, value);
		}
	}
}

export default SubscriberService;

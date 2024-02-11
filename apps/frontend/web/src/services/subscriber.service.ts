import { BACKEND_API_URL } from '@/constants';
import type { OffsetPage, Subscriber, Sort } from '@lyra/vm-core';

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
		filter?: string,
		sort?: Sort,
		size: number = 10,
		page: number = 0
	): Promise<OffsetPage<Subscriber>> {
		const url = new URL(`${BACKEND_API_URL}newsletter/subscribers`);

		const params = new URLSearchParams();
		if (filter) {
			const filters = filter.split('&');
			filters.forEach((f) => {
				const [key, value] = f.split('=');
				params.append(key, value);
			});
		}
		if (sort) {
      const sortQuery = sort.toQueryString();
      const [key, value] = sortQuery.split('=');
      params.append(key, value);
		}
		params.append('size', size.toString());
		params.append('page', page.toString());

		url.search = params.toString();

		const response = await fetch(url);
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		return await response.json();
	}
}

export default SubscriberService;

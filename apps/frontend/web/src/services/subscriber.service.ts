import { BACKEND_API_URL } from '@/constants';
import type { OffsetPage, Subscriber, SubscriberFilter } from '@lyra/vm-core';

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
		filter?: SubscriberFilter,
		sort?: string,
		size: number = 10,
		page: number = 0
	): Promise<OffsetPage<Subscriber>> {
		const url = new URL(`${BACKEND_API_URL}newsletter/subscribers`);

		const params = new URLSearchParams();
		if (filter) {
			Object.entries(filter)
				.filter(([, value]) => value.length > 0 && value[0] !== '')
				.forEach(([key, value]) => {
					params.append(key, value.join(','));
				});
		}
		for (let entry of params.entries()) {
			console.log(entry);
		}
		if (sort) {
			params.append('sort', sort);
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

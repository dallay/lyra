import { BACKEND_API_URL } from '@/constants';
import type { Subscribers } from '@lyra/vm-core'

class SubscriberService {
	private static instance: SubscriberService;

	private constructor() {
	}

	public static getInstance(): SubscriberService {
		if (!SubscriberService.instance) {
			SubscriberService.instance = new SubscriberService();
		}
		return SubscriberService.instance;
	}

	public async getSubscribers(): Promise<Subscribers> {
		const response = await fetch(`${BACKEND_API_URL}newsletter/subscribers`);
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		return await response.json();
	}
}

export default SubscriberService;

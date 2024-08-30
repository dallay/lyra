import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import {OrganizationId, type Subscriber, SubscriberId, type SubscriberRequest} from "@lyra/domain";

export const useSubscriberStore = defineStore('subscriber', () => {
	const { $api } = useNuxtApp();
	const subscribers = ref<Subscriber[]>([]);

	const createSubscriber = async (organizationId: OrganizationId, request: SubscriberRequest) => {
		try {
			const subscriberId = SubscriberId.random();
			await $api.subscriber.createSubscriber(organizationId, subscriberId, request);
		} catch (error) {
			console.error(`createSubscriber error:${error}`);
		}
	};

	return { subscribers, createSubscriber };
});

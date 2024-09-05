import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import { useWorkspaceStore } from "~/store/workspace.store";
import {type Subscriber, SubscriberId, type SubscriberRequest} from "~/domain/subscriber";
import {OrganizationId} from "~/domain/organization";

export const useSubscriberStore = defineStore('subscriber', () => {
	const { $api } = useNuxtApp();
	const subscribers = ref<Subscriber[]>([]);
  const workspaceStore = useWorkspaceStore();

	const createSubscriber = async (organizationId: OrganizationId, request: SubscriberRequest) => {
		try {
			const subscriberId = SubscriberId.random();
			await $api.subscriber.createSubscriber(organizationId, subscriberId, request);
		} catch (error) {
			console.error(`createSubscriber error:${error}`);
		}
	};

const fetchAllSubscriber = async (organizationId?: OrganizationId) => {
  try {
    organizationId = organizationId || workspaceStore.getCurrentOrganizationId() || await (async () => {
      await workspaceStore.fetchWorkspaces();
      return workspaceStore.getCurrentOrganizationId();
    })();

    if (!organizationId) return;

    const response = await $api.subscriber.fetchAll(organizationId);
    subscribers.value = response.data;
  } catch (error) {
    console.error(`fetchAllSubscriber error:${error}`);
  }
}

	return { subscribers, createSubscriber, fetchAllSubscriber };
});

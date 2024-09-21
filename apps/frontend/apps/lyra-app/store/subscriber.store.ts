import { useNuxtApp } from "#app";
import { ref, h, type Component } from "vue";
import { CheckCircledIcon, CircleIcon, CircleBackslashIcon } from "@radix-icons/vue";
import { defineStore } from "pinia";
import { useWorkspaceStore } from "~/store/workspace.store";
import { useSubscriberFilterStore } from "./subscriber.filter.store";
import {
  defaultPageResponse,
  defaultSubscriberCountByStatus,
  defaultSubscriberCountByTag
} from "./subscriber.constants";
import {
  type Subscriber,
  SubscriberId,
  type SubscriberRequest,
  type SubscriberCountByStatusResponse,
  type SubscriberCountByTagsResponse,
} from "~/domain/subscriber";
import { OrganizationId } from "~/domain/organization";
import type { PageResponse } from "@lyra/shared";
import type { CriteriaQueryParams } from "~/domain/criteria";

type FilterIcons = { value: string, icon: Component };
const statusIcons: FilterIcons[] = [
  { value: "ENABLED", icon: h(CheckCircledIcon) },
  { value: "DISABLED", icon: h(CircleIcon) },
  { value: "BLOCKLISTED", icon: h(CircleBackslashIcon) },
];
const tagIcons: FilterIcons[] = [
  { value: "bug", icon: h(CircleIcon) },
  { value: "feature", icon: h(CircleIcon) },
  { value: "documentation", icon: h(CircleIcon) },
];

const buildFilters = <T extends {count: number}>(
  data: T[],
  icons: FilterIcons[],
  keyExtractor: (item: T) => string
): SubscriberCounterFilter[] => {
  return data.map(item => {
    const value = keyExtractor(item);
    return {
      value,
      label: value,
      count: item.count,
      icon: loadIcon(value, icons),
    };
  });
};

const loadIcon = (value: string, icons: FilterIcons[]): Component => {
  const icon = icons.find(icon => icon.value === value);
  return icon ? icon.icon : h(CircleIcon);
};

interface SubscriberCounterFilter {
  value: string;
  label: string;
  count: number;
  icon: Component;
}

export const useSubscriberStore = defineStore("subscriber", () => {
  const { $api } = useNuxtApp();
  const subscribers = ref<Subscriber[]>([]);
  const statuses = ref<SubscriberCounterFilter[]>(
    buildFilters(defaultSubscriberCountByStatus.data, statusIcons, item => item.status)
  );
  const tags = ref<SubscriberCounterFilter[]>(
    buildFilters(defaultSubscriberCountByTag.data, tagIcons, item => item.tag)
  );
  const workspaceStore = useWorkspaceStore();
  const subscriberFilterStore = useSubscriberFilterStore();

  const createSubscriber = async (organizationId: OrganizationId, request: SubscriberRequest) => {
    try {
      const subscriberId = SubscriberId.random();
      await $api.subscriber.createSubscriber(organizationId, subscriberId, request);
    } catch (error) {
      console.error(`createSubscriber error:${error}`);
    }
  };

  const fetchAllSubscriber = async (
    criteria: CriteriaQueryParams = subscriberFilterStore.subscriberFilterOptions
  ): Promise<PageResponse<Subscriber>> => {
    try {
      const organizationId = workspaceStore.getCurrentOrganizationId();
      if (!organizationId) return defaultPageResponse;

      const response = await $api.subscriber.fetchAll(organizationId, criteria);
      subscriberFilterStore.updatePageCursor({
        previous: response?.prevPageCursor,
        next: response?.nextPageCursor,
      });
      subscribers.value = response?.data || [];
      subscriberFilterStore.subscriberFilterOptions.cursor = subscriberFilterStore.cursorPage.next;
      return response || defaultPageResponse;
    } catch (error) {
      console.error(`fetchAllSubscriber error:${error}`);
      return defaultPageResponse;
    }
  };

  const subscriberCountByStatus = async (): Promise<SubscriberCountByStatusResponse> => {
    const organizationId = workspaceStore.getCurrentOrganizationId();
    if (!organizationId) return defaultSubscriberCountByStatus;

    try {
      const response = await $api.subscriber.countByStatus(organizationId);
      statuses.value = buildFilters(response?.data || [], statusIcons, status => status.status);
      return response || defaultSubscriberCountByStatus;
    } catch (error) {
      console.error(`subscriberCountByStatus error:${error}`);
      return defaultSubscriberCountByStatus;
    }
  };

  const subscriberCountByTags = async (): Promise<SubscriberCountByTagsResponse> => {
    const organizationId = workspaceStore.getCurrentOrganizationId();
    if (!organizationId) return defaultSubscriberCountByTag;

    try {
      const response = await $api.subscriber.countByTags(organizationId);
      tags.value = buildFilters(response?.data || [], tagIcons, tag => tag.tag);
      return response || defaultSubscriberCountByTag;
    } catch (error) {
      console.error(`subscriberCountByTags error:${error}`);
      return defaultSubscriberCountByTag;
    }
  };

  const previousPage = async () => {
    subscriberFilterStore.subscriberFilterOptions.cursor = subscriberFilterStore.cursorPage.previous;
    await fetchAllSubscriber();
  };

  const nextPage = async () => {
    subscriberFilterStore.subscriberFilterOptions.cursor = subscriberFilterStore.cursorPage.next;
    await fetchAllSubscriber();
  };

  const clearCursor = () => {
    subscriberFilterStore.subscriberFilterOptions.cursor = null;
  };

  return {
    subscribers,
    statuses,
    tags,
    createSubscriber,
    fetchAllSubscriber,
    subscriberCountByStatus,
    subscriberCountByTags,
    previousPage,
    nextPage,
    clearCursor,
  };
});

import { useNuxtApp } from "#app";
import { ref, type Component, h } from "vue";
import {
  CheckCircledIcon,
  CircleIcon,
  CircleBackslashIcon,
} from "@radix-icons/vue";
import { defineStore } from "pinia";
import { useWorkspaceStore } from "~/store/workspace.store";
import { useSubscriberFilterStore } from "./subscriber.filter.store";
import {
  type CountByStatusResponse, type CountByTagsResponse,
  type Subscriber,
  type SubscriberCountByStatusResponse,
  type SubscriberCountByTagsResponse,
  SubscriberId,
  type SubscriberRequest,
} from "~/domain/subscriber";
import { OrganizationId } from "~/domain/organization";
import { type PageResponse } from "@lyra/shared";
import {
  type CriteriaQueryParams,
} from "~/domain/criteria";

const defaultPageResponse: PageResponse<Subscriber> = {
  data: [],
  prevPageCursor: null,
  nextPageCursor: null,
};

const defaultSubscriberCountByStatus: SubscriberCountByStatusResponse = {
  data: [
    {
      status: "ENABLED",
      count: 0,
    },
    {
      status: "DISABLED",
      count: 0,
    },
    {
      status: "BLOCKLISTED",
      count: 0,
    },
  ],
};

const defaultSubscriberCountByTag: SubscriberCountByTagsResponse = {
  data: [
    {
      tag: "bug",
      count: 0,
    },
    {
      tag: "feature",
      count: 0,
    },
    {
      tag: "documentation",
      count: 0,
    },
  ],
}

type FilterIcons = { value: string, icon: Component };
const statusIcons: FilterIcons[] = [
  {
    value: "ENABLED",
    icon: h(CheckCircledIcon),
  },
  {
    value: "DISABLED",
    icon: h(CircleIcon),
  },
  {
    value: "BLOCKLISTED",
    icon: h(CircleBackslashIcon),
  },
];

const tagIcons:FilterIcons []= [
  {
    value: "bug",
    icon: h(CircleIcon),
  },
  {
    value: "feature",
    icon: h(CircleIcon),
  },
  {
    value: "documentation",
    icon: h(CircleIcon),
  },
];
const buildSubscriberStatuses = (
  data: CountByStatusResponse[]
): SubscriberCounterFilter[] => {
  return data.map((countByStatus: CountByStatusResponse) => ({
    value: countByStatus.status,
    label: countByStatus.status,
    count: countByStatus.count,
    icon: loadIcon(countByStatus.status, statusIcons),
  }));
};

const buildSubscriberTags = (
  data: CountByTagsResponse[]
): SubscriberCounterFilter[] => {
  return data.map((countByTag: CountByTagsResponse) => ({
    value: countByTag.tag,
    label: countByTag.tag,
    count: countByTag.count,
    icon: loadIcon(countByTag.tag, tagIcons),
  }));
}

const loadIcon = (value: string, icons: FilterIcons[]): Component => {
  const icon = icons.find((icon) => icon.value === value);
  return icon ? icon.icon : h(CircleIcon);
};

interface SubscriberCounterFilter {
  value: string;
  label: string;
  count: number;
  icon: Component;
}

/**
 * Defines a store for managing subscribers.
 */
export const useSubscriberStore = defineStore("subscriber", () => {
  const { $api } = useNuxtApp();
  const subscribers = ref<Subscriber[]>([]);
  const statuses = ref<SubscriberCounterFilter[]>(
    buildSubscriberStatuses(defaultSubscriberCountByStatus.data)
  );
  const tags = ref<SubscriberCounterFilter[]>(
    buildSubscriberTags(defaultSubscriberCountByTag.data)
  );
  const workspaceStore = useWorkspaceStore();
  const subscriberFilterStore = useSubscriberFilterStore();

  /**
   * Creates a new subscriber.
   * @param {OrganizationId} organizationId - The ID of the organization.
   * @param {SubscriberRequest} request - The request payload for creating a subscriber.
   */
  const createSubscriber = async (
    organizationId: OrganizationId,
    request: SubscriberRequest
  ) => {
    try {
      const subscriberId = SubscriberId.random();
      await $api.subscriber.createSubscriber(
        organizationId,
        subscriberId,
        request
      );
    } catch (error) {
      console.error(`createSubscriber error:${error}`);
    }
  };

  /**
   * Fetches all subscribers based on the provided filter options.
   * @param {CriteriaQueryParams} criteria - The criteria query parameters. Defaults to the current filter options.
   * @returns {Promise<PageResponse<Subscriber>>} - A promise that resolves to the page response containing subscribers.
   */
  const fetchAllSubscriber = async (
    criteria: CriteriaQueryParams = subscriberFilterStore.subscriberFilterOptions
  ): Promise<PageResponse<Subscriber>> => {
    try {
      const organizationId = await workspaceStore.getCurrentOrganizationId();

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


  const subscriberCountByStatus =
    async (): Promise<SubscriberCountByStatusResponse> => {
      const organizationId = await workspaceStore.getCurrentOrganizationId();
      if (!organizationId) return defaultSubscriberCountByStatus;

      try {
        const response = await $api.subscriber.countByStatus(organizationId);
        statuses.value = buildSubscriberStatuses(response?.data || []);
        return response || defaultSubscriberCountByStatus;
      } catch (error) {
        console.error(`subscriberCountByStatus error:${error}`);
        return defaultSubscriberCountByStatus;
      }
    };

  const subscriberCountByTags =
    async (): Promise<SubscriberCountByTagsResponse> => {
      const organizationId = await workspaceStore.getCurrentOrganizationId();
      if (!organizationId) return defaultSubscriberCountByTag;

      try {
        const response = await $api.subscriber.countByTags(organizationId);
        tags.value = buildSubscriberTags(response?.data || []);
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
    subscriberFilterStore. subscriberFilterOptions.cursor = null;
  }

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
    clearCursor
    };
});

import {useNuxtApp} from "#app";
import {ref, type Component, h} from "vue";
import {
  ArrowDownIcon,
  ArrowRightIcon,
  ArrowUpIcon,
  CheckCircledIcon,
  CircleIcon,
  CircleBackslashIcon
} from '@radix-icons/vue'
import {defineStore} from "pinia";
import {useWorkspaceStore} from "~/store/workspace.store";
import {
  type CountByStatusResponse,
  type Subscriber,
  type SubscriberCountByStatusResponse,
  SubscriberId,
  type SubscriberRequest
} from "~/domain/subscriber";
import {OrganizationId} from "~/domain/organization";
import type {PageResponse} from "@lyra/shared";
import {
  type CriteriaParam,
  type CriteriaQueryParams,
  DEFAULT_PAGE_SIZE,
  type SortParam
} from "~/domain/criteria";

const defaultPageResponse: PageResponse<Subscriber> = { data: [], nextPageCursor: null };

const defaultSubscriberCountByStatus: SubscriberCountByStatusResponse = {
	data: [
		{
			status: 'ENABLED',
			count: 0,
		},
		{
			status: 'DISABLED',
			count: 0,
		},
		{
			status: 'BLOCKLISTED',
			count: 0,
		},
	],
};

const statusIcons = [
  {
    value: 'ENABLED',
    icon: h(CheckCircledIcon),
  },
  {
    value: 'DISABLED',
    icon: h(CircleIcon),
  },
  {
    value: 'BLOCKLISTED',
    icon: h(CircleBackslashIcon),
  },
]
const  buildSubscriberStatuses = (data: CountByStatusResponse[]): SubscriberStatuses[] => {
  return data.map((countByStatus: CountByStatusResponse) => ({
    value: countByStatus.status,
    label: countByStatus.status.charAt(0).toUpperCase() + countByStatus.status.slice(1).toLowerCase(),
    count: countByStatus.count,
    icon: loadIcon(countByStatus.status),
  }));
}


const loadIcon = (status: string) => {
  const icon = statusIcons.find((icon) => icon.value === status)
  return icon ? icon.icon : h(CircleIcon)
}

interface SubscriberStatuses {
  value: string;
  label: string;
  count: number;
  icon: Component;
}

/**
 * Defines a store for managing subscribers.
 */
export const useSubscriberStore = defineStore('subscriber', () => {
	const { $api } = useNuxtApp();
	const subscribers = ref<Subscriber[]>([]);
  const statuses = ref<SubscriberStatuses[]>(buildSubscriberStatuses(defaultSubscriberCountByStatus.data));
	const workspaceStore = useWorkspaceStore();
	const subscriberFilterOptions = ref<CriteriaQueryParams>({ size: DEFAULT_PAGE_SIZE });

	/**
	 * Creates a new subscriber.
	 * @param {OrganizationId} organizationId - The ID of the organization.
	 * @param {SubscriberRequest} request - The request payload for creating a subscriber.
	 */
	const createSubscriber = async (organizationId: OrganizationId, request: SubscriberRequest) => {
		try {
			const subscriberId = SubscriberId.random();
			await $api.subscriber.createSubscriber(organizationId, subscriberId, request);
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
		criteria: CriteriaQueryParams = subscriberFilterOptions.value,
	): Promise<PageResponse<Subscriber>> => {
		try {
			const organizationId = await workspaceStore.getCurrentOrganizationId();

			if (!organizationId) return defaultPageResponse;

			const response = await $api.subscriber.fetchAll(organizationId, criteria);
			subscribers.value = response?.data || [];
			return response || defaultPageResponse;
		} catch (error) {
			console.error(`fetchAllSubscriber error:${error}`);
			return defaultPageResponse;
		}
	};

	/**
	 * Sets the subscriber filter options.
	 * @param {CriteriaQueryParams} options - The filter options to set.
	 */
	const setSubscriberFilterOptions = (options: CriteriaQueryParams) => {
		subscriberFilterOptions.value = options;
	};

	/**
	 * Sets the criteria for filtering subscribers.
	 * @param {CriteriaParam} criteria - The criteria to set.
	 * @param {boolean} isSearch - Whether the criteria is for searching.
	 */
	const addSubscriberFilterCriteria = (criteria: CriteriaParam, isSearch: boolean = false) => {
		const criteriaSet: Set<CriteriaParam> = isSearch
			? subscriberFilterOptions.value.searchCriteria ?? new Set<CriteriaParam>()
			: subscriberFilterOptions.value.filterCriteria ?? new Set<CriteriaParam>();

		// Add the criteria to the set if it doesn't exist already
		criteriaSet.add(criteria);

		// Set the criteria set based on whether it's a search or filter criteria
		if (isSearch) {
			subscriberFilterOptions.value.searchCriteria = criteriaSet;
		} else {
			subscriberFilterOptions.value.filterCriteria = criteriaSet;
		}
	};

	/**
	 * Add All Subscriber Filter Criteria to the subscriber filter options.
	 * @param {CriteriaParam[]} criteria - The criteria to set.
	 */
  const addAllSubscriberFilterCriteria = (criteria: CriteriaParam[]) => {
    const criteriaSet = subscriberFilterOptions.value.filterCriteria ?? new Set<CriteriaParam>();
    criteriaSet.clear();
    criteria.forEach(c => criteriaSet.add(c));
    subscriberFilterOptions.value.filterCriteria = criteriaSet;
  };

	/**
	 * Removes a specific criteria from the subscriber filter options.
	 * @param {CriteriaParam} criteria - The criteria to remove.
	 * @param {boolean} isSearch - Whether the criteria is for searching.
	 */
	const removeSubscriberFilterCriteria = (criteria: CriteriaParam, isSearch: boolean = false) => {
		const criteriaSet: Set<CriteriaParam> = isSearch
			? subscriberFilterOptions.value.searchCriteria ?? new Set<CriteriaParam>()
			: subscriberFilterOptions.value.filterCriteria ?? new Set<CriteriaParam>();

		// Remove the criteria from the set if it exists
		criteriaSet.delete(criteria);

		// Set the criteria set based on whether it's a search or filter criteria
		if (isSearch) {
			subscriberFilterOptions.value.searchCriteria = criteriaSet;
		} else {
			subscriberFilterOptions.value.filterCriteria = criteriaSet;
		}
	};

	/**
	 * Resets the subscriber filter options.
	 *
	 */
	const resetSubscriberFilterOptions = () => {
		subscriberFilterOptions.value = { size: DEFAULT_PAGE_SIZE };
	};

	/**
	 * Sets the sorting options for the subscriber query.
	 * @param {SortParam} sort - The sorting options to set.
	 */
	const setSubscriberFilterSort = (sort: SortParam) => {
		subscriberFilterOptions.value.sortCriteria =
			subscriberFilterOptions.value.sortCriteria ?? new Set<SortParam>();

		subscriberFilterOptions.value.sortCriteria.clear();
		subscriberFilterOptions.value.sortCriteria.add(sort);
	};

	const subscriberCountByStatus = async (): Promise<SubscriberCountByStatusResponse> => {
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

	return {
		subscribers,
    statuses,
		subscriberFilterOptions,
		createSubscriber,
		fetchAllSubscriber,
		setSubscriberFilterOptions,
		addSubscriberFilterCriteria,
		addAllSubscriberFilterCriteria,
		removeSubscriberFilterCriteria,
		resetSubscriberFilterOptions,
		setSubscriberFilterSort,
		subscriberCountByStatus,
	};
});

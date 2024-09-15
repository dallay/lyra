import { ref } from "vue";
import { defineStore } from "pinia";
import {
  type DateValue,
  getLocalTimeZone,
  today,
} from "@internationalized/date";
import {
  DEFAULT_PAGE_SIZE,
  type CriteriaParam,
  type CriteriaQueryParams,
  type SortParam,
} from "~/domain/criteria";
import type { DateRange } from "radix-vue";

type CursorPage = {
  previous: string | null | undefined;
  next: string | null | undefined;
};

const defaultCriteria: CriteriaQueryParams = {
  size: DEFAULT_PAGE_SIZE,
  cursor: null,
};

export const useSubscriberFilterStore = defineStore("subscriberFilter", () => {
  const subscriberFilterOptions = ref<CriteriaQueryParams>(defaultCriteria);
  const cursorPage = ref<CursorPage>({ previous: null, next: null });
  const localTimeZone = getLocalTimeZone();
  const todayDate = today(localTimeZone);
  const daysBefore = 30;
  const daysAfter = 30;
  const defaultFilterDateRange = {
    start: todayDate.subtract({ days: daysBefore }).toString(),
    end: todayDate.add({ days: daysAfter }).toString(),
  };
  const filtersDateRange = ref(defaultFilterDateRange);
  const showAdvancedFilters = ref(false);

  function updateValuesToFilterDateRange(newRange: DateRange) {
    filtersDateRange.value = {
      start: newRange?.start?.toString() || filtersDateRange.value.start,
      end: newRange?.end?.toString() || filtersDateRange.value.end,
    };
    subscriberFilterOptions.value.cursor = null;
    const createdAtCriteria: CriteriaParam = {
      column: "createdAt",
      values: [
        {
          operator: "gte",
          value: filtersDateRange.value.start,
        },
        {
          operator: "lt",
          value: filtersDateRange.value.end,
        },
      ],
    };
    addSubscriberFilterCriteria(createdAtCriteria);
  }

  function updateStartDate(newStartDate: DateValue) {
    filtersDateRange.value.start = newStartDate?.toString();
  }

  function updatePageCursor(page: CursorPage) {
    cursorPage.value = page;
  }

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
   */
  const addSubscriberFilterCriteria = (criteria: CriteriaParam) => {
    const criteriaSet: Set<CriteriaParam> =
      subscriberFilterOptions.value.filterCriteria ?? new Set<CriteriaParam>();

    const existingCriteria = Array.from(criteriaSet).find(
      (c) => c.column === criteria.column
    );

    if (existingCriteria) {
      criteriaSet.delete(existingCriteria);
    }

    criteriaSet.add(criteria);

    subscriberFilterOptions.value.filterCriteria = criteriaSet;
  };

  /**
   * Add All Subscriber Filter Criteria to the subscriber filter options.
   * @param {CriteriaParam[]} criteria - The criteria to set.
   */
  const addAllSubscriberFilterCriteria = (criteria: CriteriaParam[]) => {
    const criteriaSet =
      subscriberFilterOptions.value.filterCriteria ?? new Set<CriteriaParam>();
    criteriaSet.clear();
    subscriberFilterOptions.value.cursor = null;
    criteria.forEach((c) => criteriaSet.add(c));
    subscriberFilterOptions.value.filterCriteria = criteriaSet;
  };

  /**
   * Removes a specific criteria from the subscriber filter options.
   * @param {CriteriaParam} criteria - The criteria to remove.
   */
  const removeSubscriberFilterCriteria = (criteria: CriteriaParam) => {
    const criteriaSet: Set<CriteriaParam> =
      subscriberFilterOptions.value.filterCriteria ?? new Set<CriteriaParam>();

    criteriaSet.delete(criteria);

    subscriberFilterOptions.value.filterCriteria = criteriaSet;
  };

  /**
   * Resets the subscriber filter options.
   *
   */
  const resetSubscriberFilterOptions = () => {
    subscriberFilterOptions.value = defaultCriteria;
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

  const resetFilterDateRange = () => {
    filtersDateRange.value = defaultFilterDateRange;
  };

  const cleanSubscriberCursor = async () => {
    subscriberFilterOptions.value.cursor = null;
    cursorPage.value = { previous: null, next: null };
  }

  return {
    updateValuesToFilterDateRange,
    updateStartDate,
    filtersDateRange,
    showAdvancedFilters,
    subscriberFilterOptions,
    cursorPage,
    updatePageCursor,
    setSubscriberFilterOptions,
    addSubscriberFilterCriteria,
    addAllSubscriberFilterCriteria,
    removeSubscriberFilterCriteria,
    resetSubscriberFilterOptions,
    setSubscriberFilterSort,
    resetFilterDateRange,
    cleanSubscriberCursor
  };
});

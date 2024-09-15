<script setup lang="ts">
import {defineProps, ref} from 'vue';
import type {Table} from '@tanstack/vue-table';
import type {Subscriber} from '@/domain/subscriber';
import {Separator} from '@/components/ui/separator';
import {Checkbox} from '@/components/ui/checkbox';
import {useSubscriberStore} from '~/store/subscriber.store';
import {useSubscriberFilterStore} from '~/store/subscriber.filter.store';
import {storeToRefs} from 'pinia';
import DateRangePicker from '~/components/DateRangePicker.vue';
import type {DateRange} from 'radix-vue';
import {parseDateTime} from '@internationalized/date';

const subscriberStore = useSubscriberStore();
const { fetchAllSubscriber } = subscriberStore;

const subscriberFilterStore = useSubscriberFilterStore();
const { filtersDateRange, showAdvancedFilters } = storeToRefs(subscriberFilterStore);
const { updateValuesToFilterDateRange, resetFilterDateRange, cleanSubscriberCursor } =
	subscriberFilterStore;

interface DataTableViewOptionsProps {
	table: Table<Subscriber>;
}

const props = defineProps<DataTableViewOptionsProps>();
const updateDateRange = async (newRange: DateRange) => {
	updateValuesToFilterDateRange(newRange);
	await fetchAllSubscriber();
};

const showSubscriberWhere = ref(false);

const  shouldShowSubscribersWhere = async () => {
  console.log('[DataTableAdvanceFilterOptions.vue] showSubscriberWhere.value:', showSubscriberWhere.value);
  await cleanSubscriberCursor();
  if (!showSubscriberWhere.value) {
    resetFilterDateRange();
  } else {
    const dateRange: DateRange = {
      start: parseDateTime(filtersDateRange.value.start),
      end: parseDateTime(filtersDateRange.value.end),
    };
    await updateDateRange(dateRange);
  }
  showSubscriberWhere.value = !showSubscriberWhere.value;
}</script>

<template>
  <div class="w-full" v-if="showAdvancedFilters">
    <Separator class="my-4" />
    <div class="flex items-center space-x-2 my-4">
      <Checkbox id="showSubscriberWhere"  v-model="showSubscriberWhere"  @click="shouldShowSubscribersWhere" />
      <label
        for="showSubscriberWhere"
        class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
      >
        Show subscribers where
      </label>
    </div>
    <DateRangePicker
      :startDate="filtersDateRange.start"
      :endDate="filtersDateRange.end"
      @update:dateRange="updateDateRange"
      :disabled="!showSubscriberWhere"
    />
  </div>
</template>

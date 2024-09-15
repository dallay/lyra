<script setup lang="ts">
import type { Table } from '@tanstack/vue-table'
import type {Subscriber} from '@/domain/subscriber';
import { ChevronLeftIcon, ChevronRightIcon, DoubleArrowLeftIcon, DoubleArrowRightIcon } from '@radix-icons/vue'

import { Button } from '@/components/ui/button'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { useSubscriberStore } from '~/store/subscriber.store';
import { useSubscriberFilterStore } from '~/store/subscriber.filter.store';
import { computed, defineProps, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
const subscriberStore = useSubscriberStore();
const subscriberFilterStore = useSubscriberFilterStore();
const { fetchAllSubscriber, previousPage, nextPage } = subscriberStore;
const { subscriberFilterOptions, cursorPage } = storeToRefs(subscriberFilterStore);

interface DataTablePaginationProps {
  table: Table<Subscriber>;
}
const props = defineProps<DataTablePaginationProps>();

const hasNextPage = computed(() => cursorPage.value.next !== null && cursorPage.value.next !== undefined);
const hasPreviousPage = computed(() => cursorPage.value.previous !== null && cursorPage.value.previous !== undefined);

const setPageSize = async (value: string) => {
  const pageSize = Number(value);
  subscriberFilterOptions.value.size = pageSize;
  await fetchAllSubscriber();
  props.table.setPageSize(pageSize);
};

onMounted(async () => {
  props.table.setPageSize(subscriberFilterOptions.value.size);
});
</script>

<template>
  <div class="flex items-center justify-between px-2">
    <div class="flex-1 text-sm text-muted-foreground">
      {{ table.getFilteredSelectedRowModel().rows.length }} of
      {{ table.getFilteredRowModel().rows.length }} row(s) selected.
    </div>
    <div class="flex items-center space-x-6 lg:space-x-8">
      <div class="flex items-center space-x-2">
        <p class="text-sm font-medium">
          Rows per page
        </p>
        <Select
          :model-value="`${table.getState().pagination.pageSize}`"
          @update:model-value="setPageSize"
        >
          <SelectTrigger class="h-8 w-[70px]">
            <SelectValue :placeholder="`${table.getState().pagination.pageSize}`" />
          </SelectTrigger>
          <SelectContent side="top">
            <SelectItem v-for="pageSize in [10, 20, 30]" :key="pageSize" :value="`${pageSize}`">
              {{ pageSize }}
            </SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div class="flex items-center space-x-2">
        <Button
          variant="outline"
          class="h-8 w-8 p-0"
          :disabled="!hasPreviousPage"
          @click="previousPage"
        >
          <span class="sr-only">Go to previous page</span>
          <ChevronLeftIcon class="h-4 w-4" />
        </Button>
        <Button
          variant="outline"
          class="h-8 w-8 p-0"
          :disabled="!hasNextPage"
          @click="nextPage"
        >
          <span class="sr-only">Go to next page</span>
          <ChevronRightIcon class="h-4 w-4" />
        </Button>
      </div>
    </div>
  </div>
</template>

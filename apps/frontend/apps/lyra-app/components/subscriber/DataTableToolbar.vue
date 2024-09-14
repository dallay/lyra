<script setup lang="ts">
import { computed, onMounted } from "vue";
import type { Table } from "@tanstack/vue-table";
import type { Subscriber } from "@/domain/subscriber";
import DataTableFacetedFilter from "./DataTableFacetedFilter.vue";
import DataTableViewOptions from "./DataTableViewOptions.vue";
import DataTableAdvanceFilterOptions from "./DataTableAdvanceFilterOptions.vue";
import { Cross2Icon, MixerVerticalIcon } from "@radix-icons/vue";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useSubscriberStore } from "~/store/subscriber.store";
import { useSubscriberFilterStore } from "~/store/subscriber.filter.store";
import { storeToRefs } from "pinia";

const subscriberStore = useSubscriberStore();
const { statuses, tags } = storeToRefs(subscriberStore);
const { fetchAllSubscriber, subscriberCountByStatus } = subscriberStore;

const subscriberFilterStore = useSubscriberFilterStore();
const { showAdvancedFilters } = storeToRefs(subscriberFilterStore);

interface DataTableToolbarProps {
  table: Table<Subscriber>;
}

const props = defineProps<DataTableToolbarProps>();

const isFiltered = computed(
  () => props.table.getState().columnFilters.length > 0
);

const resetColumnFilters = async () => {
  props.table.resetColumnFilters();
  await fetchAllSubscriber();
};

const debounce = (func: Function, wait: number) => {
  let timeout: number;
  return (...args: any[]) => {
    clearTimeout(timeout);
    timeout = window.setTimeout(() => func.apply(this, args), wait);
  };
};

const setEmailFilterValue = debounce(async (event: Event) => {
  const target = event.target as HTMLInputElement;
  await fetchAllSubscriber();
  props.table.getColumn("email")?.setFilterValue(target.value);
}, 300);

onMounted(async () => {
  await subscriberCountByStatus();
});
</script>

<template>
  <div class="flex flex-col space-y-4">
    <div class="flex items-center justify-between">
      <div class="flex flex-1 items-center space-x-2">
        <Input
          placeholder="Filter subscribers..."
          :model-value="(table.getColumn('email')?.getFilterValue() as string) ?? ''"
          class="h-8 w-[150px] lg:w-[250px]"
          @input="setEmailFilterValue"
        />
        <DataTableFacetedFilter
          v-if="table.getColumn('status')"
          :column="table.getColumn('status')"
          title="Status"
          :options="statuses"
        />

        <Button
          v-if="isFiltered"
          variant="ghost"
          class="h-8 px-2 lg:px-3"
          @click="resetColumnFilters"
        >
          Reset
          <Cross2Icon class="ml-2 h-4 w-4" />
        </Button>
      </div>
      <Button variant="outline" size="sm" class="mx-2 hidden h-8 lg:flex" @click="showAdvancedFilters = !showAdvancedFilters">
        <MixerVerticalIcon class="h-4 w-4" />
      </Button>
      <DataTableViewOptions :table="table" />
    </div>
    <div class="flex items-center justify-between">
      <DataTableAdvanceFilterOptions :table="table" />
    </div>
  </div>
</template>

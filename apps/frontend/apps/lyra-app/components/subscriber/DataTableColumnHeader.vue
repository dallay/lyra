<script setup lang="ts">
import type { Column } from '@tanstack/vue-table'
import type {Subscriber} from '@/domain/subscriber';
import {ArrowDownIcon, ArrowUpIcon, CaretSortIcon, EyeNoneIcon} from '@radix-icons/vue'

import { cn } from '@/lib/utils'
import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import { useSubscriberStore} from "~/store/subscriber.store";
import { useSubscriberFilterStore } from "~/store/subscriber.filter.store";

const subscriberFilterStore = useSubscriberFilterStore();
const { setSubscriberFilterSort, cleanSubscriberCursor } = subscriberFilterStore;
const subscriberStore = useSubscriberStore();
const { fetchAllSubscriber } = subscriberStore;


interface DataTableColumnHeaderProps {
  column: Column<Subscriber, unknown>
  title: string
}

const props = defineProps<DataTableColumnHeaderProps>()
const sortColumn = async (direction: 'asc' | 'desc') => {
  await cleanSubscriberCursor();
  setSubscriberFilterSort({ field: props.column.id, direction });
  await fetchAllSubscriber();
  props.column.toggleSorting(direction === 'desc');
};

const sortAscending = () => sortColumn('asc');
const sortDescending = () => sortColumn('desc');
</script>

<script lang="ts">
export default {
  inheritAttrs: false,
}
</script>

<template>
  <div v-if="column.getCanSort()" :class="cn('flex items-center space-x-2', $attrs.class ?? '')">
    <DropdownMenu>
      <DropdownMenuTrigger as-child>
        <Button
          variant="ghost"
          size="sm"
          class="-ml-3 h-8 data-[state=open]:bg-accent"
        >
          <span>{{ title }}</span>
          <ArrowDownIcon v-if="column.getIsSorted() === 'desc'" class="ml-2 h-4 w-4" />
          <ArrowUpIcon v-else-if=" column.getIsSorted() === 'asc'" class="ml-2 h-4 w-4" />
          <CaretSortIcon v-else class="ml-2 h-4 w-4" />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="start">
        <DropdownMenuItem @click="sortAscending">
          <ArrowUpIcon class="mr-2 h-3.5 w-3.5 text-muted-foreground/70" />
          Asc
        </DropdownMenuItem>
        <DropdownMenuItem @click="sortDescending">
          <ArrowDownIcon class="mr-2 h-3.5 w-3.5 text-muted-foreground/70" />
          Desc
        </DropdownMenuItem>
        <DropdownMenuSeparator v-if="column.getCanHide()" />
        <DropdownMenuItem v-if="column.getCanHide()" @click="column.toggleVisibility(false)">
          <EyeNoneIcon class="mr-2 h-3.5 w-3.5 text-muted-foreground/70" />
          Hide
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  </div>

  <div v-else :class="$attrs.class">
    {{ title }}
  </div>
</template>

<script setup lang="ts">
import type {Column} from '@tanstack/vue-table'
import type {Component} from 'vue'
import {computed} from 'vue'
import type {Subscriber} from '@/domain/subscriber';
import {CheckIcon, PlusCircledIcon} from '@radix-icons/vue'

import {Badge} from '@/components/ui/badge'
import {Button} from '@/components/ui/button'
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  CommandSeparator
} from '@/components/ui/command'

import {Popover, PopoverContent, PopoverTrigger,} from '@/components/ui/popover'
import {Separator} from '@/components/ui/separator'
import {cn} from '@/lib/utils'
import type {CriteriaParam, CriteriaParamValue} from "~/domain/criteria";
import {useSubscriberStore} from "~/store/subscriber.store";
import { useSubscriberFilterStore } from '~/store/subscriber.filter.store';

const subscriberStore = useSubscriberStore();
const subscriberFilterStore = useSubscriberFilterStore();
const { fetchAllSubscriber } = subscriberStore;
const { addAllSubscriberFilterCriteria } = subscriberFilterStore;

type FilterOption = {
	label: string;
	value: string;
	count?: number;
	icon?: Component;
};

interface DataTableFacetedFilter {
	column?: Column<Subscriber, any>;
	title?: string;
	options: FilterOption[];
}

const props = defineProps<DataTableFacetedFilter>();

const facets = computed(() => {
	const uniqueValues = props.column?.getFacetedUniqueValues() || new Map();
	const facetsMap = new Map<string, number>();

	props.options.forEach((option) => {
		if (option.count !== undefined && option.count !== null) {
			facetsMap.set(option.value, option.count);
		} else {
			const uniqueValueCount = uniqueValues.get(option.value);
			if (uniqueValueCount !== undefined) {
				facetsMap.set(option.value, uniqueValueCount);
			}
		}
	});
	return facetsMap;
});

const selectedValues = computed(() => new Set(props.column?.getFilterValue() as string[]));

async function toggleSelection(option: FilterOption) {
	const columnId = props.column?.id;
	if (!columnId) return;

	const isSelected = selectedValues.value.has(option.value);

	if (isSelected) {
		selectedValues.value.delete(option.value);
	} else {
		selectedValues.value.add(option.value);
	}

	if (selectedValues.value.size > 0) {
		const allCriteriaValue: CriteriaParamValue[] = Array.from(selectedValues.value).map(
			(value) => ({
				operator: 'eq',
				value: value,
			}),
		);
		const allCriteria: CriteriaParam = {
			column: columnId,
			logicalOperator: 'OR',
			values: allCriteriaValue,
		};

		addAllSubscriberFilterCriteria([allCriteria]);
	} else {
		addAllSubscriberFilterCriteria([]);
	}
	await fetchAllSubscriber();

	const filterValues = Array.from(selectedValues.value);
	props.column?.setFilterValue(filterValues.length ? filterValues : undefined);
}

async function clearFilter() {
	props.column?.setFilterValue(undefined);
	selectedValues.value.clear();
	addAllSubscriberFilterCriteria([]);
	await fetchAllSubscriber();
}
</script>

<template>
  <Popover>
    <PopoverTrigger as-child>
      <Button variant="outline" size="sm" class="h-8 border-dashed capitalize">
        <PlusCircledIcon class="mr-2 h-4 w-4" />
        {{ title }}
        <template v-if="selectedValues.size > 0">
          <Separator orientation="vertical" class="mx-2 h-4" />
          <Badge
            variant="secondary"
            class="rounded-sm px-1 font-normal lg:hidden"
          >
            {{ selectedValues.size }}
          </Badge>
          <div class="hidden space-x-1 lg:flex">
            <Badge
              v-if="selectedValues.size > 2"
              variant="secondary"
              class="rounded-sm px-1 font-normal"
            >
              {{ selectedValues.size }} selected
            </Badge>

            <template v-else>
              <Badge
                v-for="option in options
                  .filter((option) => selectedValues.has(option.value))"
                :key="option.value"
                variant="secondary"
                class="rounded-sm px-1 font-normal capitalize"
              >
                {{ option.label }}
              </Badge>
            </template>
          </div>
        </template>
      </Button>
    </PopoverTrigger>
    <PopoverContent class="w-[200px] p-0" align="start">
      <Command
        :filter-function="(list: DataTableFacetedFilter['options'], term) => list.filter(i => i.label.toLowerCase()?.includes(term)) "
      >
        <CommandInput :placeholder="title" />
        <CommandList>
          <CommandEmpty>No results found.</CommandEmpty>
          <CommandGroup>
            <CommandItem
              v-for="option in options"
              :key="option.value"
              :value="option"
              @select="async () => await toggleSelection(option)"
            >
              <div
                :class="cn(
                  'mr-2 flex h-4 w-4 items-center justify-center rounded-sm border border-primary capitalize',
                  selectedValues.has(option.value)
                    ? 'bg-primary text-primary-foreground'
                    : 'opacity-50 [&_svg]:invisible',
                )"
              >
                <CheckIcon :class="cn('h-4 w-4')" />
              </div>
              <component :is="option.icon" v-if="option.icon" class="mr-2 h-4 w-4 text-muted-foreground" />
              <span class="capitalize">{{ option.label }}</span>
              <span v-if="facets.get(option.value)" class="ml-auto flex h-4 w-4 items-center justify-center font-mono text-xs">
                {{ facets.get(option.value) }}
              </span>
            </CommandItem>
          </CommandGroup>

          <template v-if="selectedValues.size > 0">
            <CommandSeparator />
            <CommandGroup>
              <CommandItem
                :value="{ label: 'Clear filters' }"
                class="justify-center text-center"
                @select="clearFilter();"
              >
                Clear filters
              </CommandItem>
            </CommandGroup>
          </template>
        </CommandList>
      </Command>
    </PopoverContent>
  </Popover>
</template>

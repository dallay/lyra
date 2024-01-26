<template>
  <div
    class="block items-center justify-between border-b border-gray-200 bg-white p-4 sm:flex lg:mt-1.5 dark:border-gray-700 dark:bg-gray-800"
  >
    <div class="mb-1 w-full">
      <slot name="top" />
    </div>
  </div>
  <div class="flex flex-col">
    <div class="overflow-x-auto">
      <div class="inline-block min-w-full align-middle">
        <div class="overflow-hidden shadow">
          <table
            v-if="items && items.length > 0"
            class="w-full min-w-full table-fixed divide-y divide-gray-200 dark:divide-gray-600"
          >
            <thead class="bg-gray-100 dark:bg-gray-700">
              <tr>
                <th
                  v-for="column in columns"
                  :key="column.key"
                  scope="col"
                  class="p-4 text-left text-xs font-medium uppercase text-gray-500 dark:text-gray-400"
                >
                  <div class="flex items-center">
                    {{ column.label }}
                    <svg
                      v-if="column.sortable"
                      xmlns="http://www.w3.org/2000/svg"
                      :class="{
                        'border-primary-500 text-primary-500 rounded border':
                          sortKey === column.key,
                      }"
                      class="ml-1 h-6 w-6 cursor-pointer p-0.5"
                      fill="currentColor"
                      viewBox="0 0 256 256"
                      @click="onColumnHeaderClick(column)"
                    >
                      <path
                        v-if="sortDesc"
                        fill="currentColor"
                        d="M42 128a6 6 0 0 1 6-6h72a6 6 0 0 1 0 12H48a6 6 0 0 1-6-6m6-58h56a6 6 0 0 0 0-12H48a6 6 0 0 0 0 12m136 116H48a6 6 0 0 0 0 12h136a6 6 0 0 0 0-12m44.24-102.24l-40-40a6 6 0 0 0-8.48 0l-40 40a6 6 0 0 0 8.48 8.48L178 62.49V144a6 6 0 0 0 12 0V62.49l29.76 29.75a6 6 0 0 0 8.48-8.48"
                      />
                      <path
                        v-else
                        fill="currentColor"
                        d="M126 128a6 6 0 0 1-6 6H48a6 6 0 0 1 0-12h72a6 6 0 0 1 6 6M48 70h136a6 6 0 0 0 0-12H48a6 6 0 0 0 0 12m56 116H48a6 6 0 0 0 0 12h56a6 6 0 0 0 0-12m124.24-22.24a6 6 0 0 0-8.48 0L190 193.51V112a6 6 0 0 0-12 0v81.51l-29.76-29.75a6 6 0 0 0-8.48 8.48l40 40a6 6 0 0 0 8.48 0l40-40a6 6 0 0 0 0-8.48"
                      />
                    </svg>
                  </div>
                </th>
              </tr>
            </thead>

            <tbody class="divide-y divide-gray-200 bg-white dark:divide-gray-700 dark:bg-gray-800">
              <tr
                v-for="item in sortItems"
                :key="item.id"
                class="hover:bg-gray-100 dark:hover:bg-gray-700"
              >
                <td
                  v-for="column in columns"
                  :key="column.key"
                  class="whitespace-nowrap p-4 text-sm font-normal text-gray-500 dark:text-gray-400"
                >
                  <div class="text-base font-semibold text-gray-900 dark:text-white">
                    <slot
                      :name="column.key"
                      :item="item"
                    >
                      {{ item[column.key] }}
                    </slot>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <template v-else>
            <slot name="no-data">
              <div class="flex flex-col items-center justify-center py-12">
                <svg
                  class="h-12 w-12"
                  fill="currentColor"
                  viewBox="0 0 256 256"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill="currentColor"
                    d="M128 26c-52.71 0-94 23.72-94 54v96c0 30.28 41.29 54 94 54s94-23.72 94-54V80c0-30.28-41.29-54-94-54m0 12c44.45 0 82 19.23 82 42s-37.55 42-82 42s-82-19.23-82-42s37.55-42 82-42m82 138c0 22.77-37.55 42-82 42s-82-19.23-82-42v-21.21C62 171.16 92.37 182 128 182s66-10.84 82-27.21Zm0-48c0 22.77-37.55 42-82 42s-82-19.23-82-42v-21.21C62 123.16 92.37 134 128 134s66-10.84 82-27.21Z"
                  />
                </svg>

                <p class="mt-2 text-sm font-medium text-gray-500 dark:text-gray-400">
                  No data available
                </p>
              </div>
            </slot>
          </template>
        </div>
      </div>
    </div>
  </div>
  <slot name="footer" />
</template>

<script setup lang="ts">
import { computed, defineProps, PropType, ref } from 'vue';
import { ColumnInfo } from './types';

const props = defineProps({
	columns: {
		type: Array as PropType<ColumnInfo[]>,
		required: true,
	},
	items: {
		type: Array as PropType<unknown[]>,
		required: true,
	},
});
const defaultSortKey = props.columns.find((column) => column.sortable)?.key ?? '';
const sortKey = ref(defaultSortKey);
const sortDesc = ref(false);

const sortItems = computed(() => {
	if (sortKey.value) {
		const sortedItems = [...props.items];
		sortedItems.sort((a, b) => {
			const aValue = a[sortKey.value];
			const bValue = b[sortKey.value];
			if (aValue < bValue) {
				return sortDesc.value ? 1 : -1;
			}
			if (aValue > bValue) {
				return sortDesc.value ? -1 : 1;
			}
			return 0;
		});
		return sortedItems;
	}
	return props.items;
});

const onColumnHeaderClick = (column: ColumnInfo) => {
	if (column.sortable) {
		sortKey.value = column.key;
		sortDesc.value = !sortDesc.value;
	}
};
</script>

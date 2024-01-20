<template>
  <div
    class="block items-center justify-between border-b border-gray-200 bg-white p-4 sm:flex lg:mt-1.5 dark:border-gray-700 dark:bg-gray-800"
  >
    <div class="mb-1 w-full">
      <slot name="top">
        Top Table
      </slot>
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
                  {{ column.label }}
                </th>
              </tr>
            </thead>

            <tbody class="divide-y divide-gray-200 bg-white dark:divide-gray-700 dark:bg-gray-800">
              <tr
                v-for="item in items"
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
              No data available
            </slot>
          </template>
        </div>
      </div>
    </div>
  </div>
  <slot name="footer">
    Footer Table
  </slot>
</template>

<script setup lang="ts">
import { defineProps, PropType } from 'vue';
import { ColumnInfo } from './types';

defineProps({
	columns: {
		type: Array as PropType<ColumnInfo[]>,
		required: true,
	},
	items: {
		type: Array as PropType<unknown[]>,
		required: true,
	},
});
</script>

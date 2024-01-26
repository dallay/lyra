<template>
  <div class="flex flex-col">
    <label
      v-if="label"
      :for="label"
      class="mb-2 block text-sm font-medium text-gray-900 dark:text-white"
    >{{ label }}</label
    >
    <select
      :id="label"
      v-model="model"
      class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400 dark:focus:border-blue-500 dark:focus:ring-blue-500"
    >
      <option
        v-for="option in options"
        :key="keyProp ? option[keyProp] : option"
        :value="keyProp ? option[keyProp] : option"
        :selected="isOptionSelected(option)"
      >
        <slot name="option" :option="option">
          {{ option }}
        </slot>
      </option>
    </select>
  </div>
</template>

<script setup lang="ts">
import { defineProps, PropType } from 'vue';

const model = defineModel(
  {
    type: null as PropType<unknown>,
  },
);
const props = defineProps({
  keyProp: {
    type: String,
    default: '',
  },
  label: {
    type: String,
    default: '',
  },
  options: {
    type: Array as PropType<unknown[]>,
    required: true,
  },
});

function isOptionSelected(option: unknown) {
  if (!model.value) {
    return false;
  }

  if (props.keyProp) {
    return option[props.keyProp] === model.value?.[props.keyProp];
  }

  return option === model.value;
}
</script>

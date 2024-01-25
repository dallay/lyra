<template>
  <div class="flex flex-col">
    <label v-if="label" :for="label"
           class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">{{ label }}</label>
    <select :id="label" :value="modelValue"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
      <option v-for="option in options" :key="keyProp ? option[keyProp] : option"
              :value="keyProp ? option[keyProp] : option"
              :selected="isOptionSelected(option)" @click="onOptionSelected(option)">
        <slot name="option" :option="option">
          {{ keyProp ? option[keyProp] : option }}
        </slot>
      </option>
    </select>

  </div>
</template>

<script setup lang="ts">
import { defineEmits, defineProps, PropType, ref } from 'vue';

const props = defineProps({
  modelValue: {
    type: null as PropType<unknown>,
  },
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

const emit = defineEmits<{
  (e: 'update:modelValue', value: unknown): void;
}>();

function isOptionSelected(option: unknown) {
  if (!props.modelValue) {
    return false;
  }

  if (props.keyProp) {
    return option[props.keyProp] === props.modelValue?.[props.keyProp];
  }

  return option === props.modelValue;
}

function onOptionSelected(option: unknown) {
  emit('update:modelValue', isOptionSelected(option) ? undefined : option);
}
</script>

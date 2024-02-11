<script setup lang="ts">
import SvgIcon from '@/components/media/SvgIcon.vue';
import { computed, type PropType } from 'vue';
import { type FilterType } from '@lyra/vm-core';

const props = defineProps({
  id: {
    type: String,
    default: crypto.randomUUID(),
  },
  type: {
    type: String as PropType<FilterType>,
    default: 'text',
  },
  placeholder: {
    type: String,
    default: '',
  },
});

const model = defineModel<string | number | Date>({
  default: '',
});
const emit = defineEmits(['clearInput']);
const clearInput = () => {
  model.value = '';
  emit('clearInput');
};
const showClearButton = computed(() => {
  return (
    model.value !== '' &&
    model.value !== null &&
    model.value !== undefined &&
    props.type !== 'select' &&
    props.type !== 'date'
  );
});
</script>

<template>
  <div class="relative">
    <input
      :id="id"
      v-model="model"
      :type="type"
      :placeholder="placeholder"
      class="block rounded p-2.5 w-full z-20 text-sm text-tertiary-900 bg-tertiary-50 border-s-tertiary-50 border-s-2 border border-tertiary-300 focus:ring-secondary-500 focus:border-secondary-500 dark:bg-tertiary-700 dark:border-s-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:text-white dark:focus:border-secondary-500"
      required
    />
    <button
      v-if="showClearButton"
      :id="`${id}-clear`"
      type="button"
      class="absolute end-2.5 top-2.5 bottom-2.5 inline-flex items-center p-1 ms-2 text-sm text-secondary-400 bg-transparent rounded-sm"
      @click="clearInput"
    >
      <SvgIcon name="close" class="w-5 h-5 text-tertiary-400 dark:text-tertiary-300" />
      <span class="sr-only">Clear input</span>
    </button>
  </div>
</template>

<style scoped>

</style>

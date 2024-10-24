<template>
  <div class="flex items-center gap-2">
    <input
      class="h-2 bg-neutral-200 border-0 rounded appearance-none fill-neutral-300"
      type="range"
      min="25"
      max="100"
      step="25"
      @input="handleChange"
      :value="currentValue"
    />
    <span class="text-xs font-semibold text-neutral-500 select-none">{{ value }}%</span>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, defineProps } from 'vue';

interface ImageBlockWidthProps {
  value: number;
  onChange: (value: number) => void;
}

const props = defineProps<ImageBlockWidthProps>();

const currentValue = ref(props.value);

watch(() => props.value, (newValue) => {
  currentValue.value = newValue;
});

const handleChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  const nextValue = parseInt(target.value);
  props.onChange(nextValue);
  currentValue.value = nextValue;
};
</script>

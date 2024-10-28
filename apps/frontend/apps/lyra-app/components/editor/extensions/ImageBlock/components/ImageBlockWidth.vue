<template>
  <div class="flex items-center gap-2 w-full">
    <Slider
      v-model="currentValue"
      :min="25"
      :max="100"
      :step="25"
      @update:model-value="handleChange"
    />
    <span class="text-xs font-semibold text-neutral-500 select-none">{{ currentValue[0] }}%</span>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, defineProps } from 'vue';
import { Slider } from '@/components/ui/slider';

interface ImageBlockWidthProps {
  value: number;
  onChange: (value: number) => void;
}

const props = defineProps<ImageBlockWidthProps>();

const currentValue = ref([props.value]);

watch(() => props.value, (newValue) => {
  currentValue.value = [newValue];
});

const handleChange = (value: number[] | undefined) => {
  if (value && value.length > 0) {
    const nextValue = value[0];
    props.onChange(nextValue);
    currentValue.value = [nextValue];
  }
};
</script>

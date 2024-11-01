<script lang="ts" setup>
import { computed, defineProps } from 'vue';
import MenuPickerPopover from "@/components/editor/extensions/menus/MenuPickerPopover.vue";

const FONT_SIZES = [
  { key: 'smaller', label: 'Smaller', value: '12px', style: { fontSize: '12px' } },
  { key: 'small', label: 'Small', value: '14px', style: { fontSize: '14px' } },
  { key: 'medium', label: 'Medium', value: '', style: { fontSize: '16px' } },
  { key: 'large', label: 'Large', value: '18px', style: { fontSize: '18px' } },
  { key: 'extra-large', label: 'Extra Large', value: '24px', style: { fontSize: '24px' } },
];

export type FontSizePickerProps = {
  onChange: (value: string) => void;
  value: string;
};

const props = defineProps<FontSizePickerProps>();

const currentSizeLabel = computed(() => {
  const activeSize = FONT_SIZES.find(size => size.value === props.value);
  return activeSize ? activeSize.label : 'Medium';
});
</script>

<template>
  <MenuPickerPopover
    :options="FONT_SIZES"
    :activeValue="props.value"
    :isActive="!!props.value"
    @select="props.onChange"
  >
    <template #triggerContent>
      {{ currentSizeLabel }}
    </template>
  </MenuPickerPopover>
</template>

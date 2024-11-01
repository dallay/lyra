<script setup lang="ts">
import { computed, defineProps } from "vue";
import MenuPickerPopover from "@/components/editor/extensions/menus/MenuPickerPopover.vue";

const FONT_FAMILY_OPTIONS = [
  { key: "inter", label: "Inter", value: "Inter", style: { fontFamily: "Inter" } },
  { key: "arial", label: "Arial", value: "Arial", style: { fontFamily: "Arial" } },
  // Add other font options here
];

const props = defineProps<{
  onChange: (value: string) => void;
  value: string;
}>();

const activeLabel = computed(() => {
  const activeOption = FONT_FAMILY_OPTIONS.find(opt => opt.value === props.value);
  return activeOption ? activeOption.label : "Default";
});
</script>

<template>
  <MenuPickerPopover
    :options="FONT_FAMILY_OPTIONS"
    :activeValue="props.value || ''"
    :isActive="!!props.value"
    @select="props.onChange"
  >
    <template #triggerContent>
      {{ activeLabel }}
    </template>
  </MenuPickerPopover>
</template>

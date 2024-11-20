<template>
  <Popover>
    <PopoverTrigger asChild>
      <Button variant="ghost" :active="isActive">
        <slot name="triggerContent" />
        <Icon name="ChevronDown" class="w-2 h-2" />
      </Button>
    </PopoverTrigger>
    <PopoverContent class="flex flex-col gap-1 px-2 py-4 w-36">
      <template v-for="option in options" :key="option.key">
        <Button
          @click="() => handleSelect(option.value)"
          :active="activeValue === option.value"
          variant="ghost"
          class="flex items-center justify-start"
          >
          <span :style="option.style">
          {{ option.label }}
          </span>
        </Button>
      </template>
    </PopoverContent>
  </Popover>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, type HTMLAttributes } from "vue";
import { Button } from "~/components/ui/button";
import { Icon } from "~/components/ui/icon";
import { Popover, PopoverContent, PopoverTrigger } from "~/components/ui/popover";

export type PickerOption = {
  key: string;
  label: string;
  value: string;
  style?: HTMLAttributes["style"];
};

const props = defineProps({
  options: { type: Array as () => PickerOption[], required: true },
  activeValue: { type: String, required: true },
  isActive: { type: Boolean, required: true }
});
const emit = defineEmits(["select"]);

interface HandleSelect {
  (value: string): void;
}

const handleSelect: HandleSelect = (value) => {
  emit("select", value);
};
</script>

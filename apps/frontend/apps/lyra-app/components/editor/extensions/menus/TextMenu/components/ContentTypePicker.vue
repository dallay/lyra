<script lang="ts" setup>
import { Icon, type IconType } from "@/components/ui/icon";
import { Button } from "@/components/ui/button";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { computed, defineProps } from "vue";
export type ContentTypePickerOption = {
  label: string;
  id: string;
  type: "option";
  disabled: () => boolean;
  isActive: () => boolean;
  onClick: () => void;
  icon: IconType;
};

export type ContentTypePickerCategory = {
  label: string;
  id: string;
  type: "category";
};

export type ContentPickerOptions = Array<
  ContentTypePickerOption | ContentTypePickerCategory
>;

export type ContentTypePickerProps = {
  options: ContentPickerOptions;
};

const isOption = (
  option: ContentTypePickerOption | ContentTypePickerCategory
): option is ContentTypePickerOption => option.type === "option";
const isCategory = (
  option: ContentTypePickerOption | ContentTypePickerCategory
): option is ContentTypePickerCategory => option.type === "category";

const props = defineProps<ContentTypePickerProps>();

const activeItem = computed(() =>
  props.options.find((option) => option.type === "option" && option.isActive())
);
</script>

<template>
  <Popover>
    <PopoverTrigger asChild>
      <Button
        variant="ghost"
        :active="activeItem?.id !== 'paragraph' && !!activeItem?.type"
      >
        <Icon
          :name="
            (activeItem?.type === 'option' && activeItem.icon) || 'Pilcrow'
          "
        />
        <Icon name="ChevronDown" class="w-2 h-2" />
      </Button>
    </PopoverTrigger>
    <PopoverContent class="flex flex-col gap-1 px-2 py-4 w-36">
      <template v-for="option in props.options" :key="option.id">
        <div v-if="isCategory(option)" class="mt-2 first:mt-0">
          <span>{{ option.label }}</span>
        </div>
        <Button
          v-if="isOption(option)"
          @click="option.onClick"
          :active="option.isActive()"
          variant="ghost"
          class="flex items-center justify-start"
        >
          <Icon :name="option.icon" class="w-4 h-4 mr-1" />
          {{ option.label }}
        </Button>
      </template>
    </PopoverContent>
  </Popover>
</template>

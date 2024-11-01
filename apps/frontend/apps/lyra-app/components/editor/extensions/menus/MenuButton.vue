<template>
  <TooltipProvider>
    <Tooltip>
      <TooltipTrigger>
        <Toggle
          :arial-label="label"
          v-model:pressed="isActive"
          @click="onClick"
          class="flex items-center gap-1"
        >
          <Icon :name="icon" />
          <span class="sr-only">{{ label }}</span>
        </Toggle>
      </TooltipTrigger>
      <TooltipContent>
        <span class="flex items-center gap-0.5">
          {{ label }}
          <ShortcutKey
            v-if="shortcut"
            v-for="shortcutKey in shortcut"
            :key="shortcutKey"
            :children="shortcutKey"
          />
        </span>
      </TooltipContent>
    </Tooltip>
  </TooltipProvider>
</template>

<script setup lang="ts">
import { defineProps, computed } from "vue";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { Icon } from "@/components/ui/icon";
import { Toggle } from "~/components/ui/toggle";
import ShortcutKey from "./ShortcutKey.vue";

export interface MenuButtonProps {
  icon: string;
  label: string;
  isActive?: boolean | (() => boolean);
  shortcut?: string[] | (() => string[]);
  onClick: () => void;
}
const props = defineProps<MenuButtonProps>();

const isActive = ref(
  typeof props.isActive === "function" ? props.isActive() : props.isActive ?? false
);

const shortcut = computed(() =>
  typeof props.shortcut === "function" ? props.shortcut() : props.shortcut ?? []
);
</script>

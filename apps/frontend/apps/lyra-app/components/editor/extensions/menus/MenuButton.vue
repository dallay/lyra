<template>
  <TooltipProvider>
    <Tooltip>
      <TooltipTrigger>
        <Button
          variant="ghost"
          class="flex"
          :active="isActive"
          @click="onClick"
        >
          <Icon :name="icon" />
          <span class="sr-only">{{ label }}</span>
        </Button>
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
import { Button } from "@/components/ui/button";
import { Icon } from "@/components/ui/icon";
import ShortcutKey from "./ShortcutKey.vue";

export interface MenuButtonProps {
  icon: string;
  label: string;
  isActive?: boolean | (() => boolean);
  shortcut?: string[] | (() => string[]);
  onClick: () => void;
}
const props = defineProps<MenuButtonProps>();

const isActive = computed(() =>
  typeof props.isActive === "function" ? props.isActive() : props.isActive ?? false
);

const shortcut = computed(() =>
  typeof props.shortcut === "function" ? props.shortcut() : props.shortcut ?? []
);
</script>

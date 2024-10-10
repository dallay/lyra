<script setup lang="ts">
import { defineProps, withDefaults, ref, type HTMLAttributes } from "vue";
import { ChevronDown, ChevronUp } from "lucide-vue-next";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuTrigger,
  DropdownMenuItem,
} from "@/components/ui/dropdown-menu";
import { cn } from '@/lib/utils'
import type { ActionSplitButtonVariants } from ".";

interface ActionSplitButtonProps {
  variant?: ActionSplitButtonVariants['variant'];
  size?: ActionSplitButtonVariants['size'];
  class?: HTMLAttributes['class'];
}

const props = withDefaults(defineProps<ActionSplitButtonProps>(), {
  class: '',
  size: 'sm',
  variant: 'outline',
});

const isDropdownOpen = ref(false);

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const handleDropdownClose = (open: boolean) => {
  if (!open) {
    isDropdownOpen.value = false;
  }
};
</script>

<template>
  <div
    role="group"
    :class="cn('relative z-0 inline-flex shadow-sm rounded-md w-full', props.class)"
  >
    <Button
      asChild
      :variant="variant"
      :size="size"
      :class="cn('rounded-r-none border-r-0 flex items-center justify-start', props.class)"
    >
      <slot />
    </Button>

    <DropdownMenu @update:open="handleDropdownClose">
      <DropdownMenuTrigger as-child @click="toggleDropdown">
        <Button
          :variant="variant"
          :size="size"
          :class="cn('rounded-l-none px-2', props.class)"
        >
          <component
            :is="isDropdownOpen ? ChevronUp : ChevronDown"
            class="h-4 w-4 transition ease-out transform duration-100"
          />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <slot name="options">
          <DropdownMenuItem asChild>
            <div class="flex items-center justify-start">
              <router-link to="/" class="flex flex-col items-start">
                <span class="text-left">Default Item</span>
                <span class="text-sm text-gray-500 text-left">Default Description</span>
              </router-link>
            </div>
          </DropdownMenuItem>
        </slot>
      </DropdownMenuContent>
    </DropdownMenu>
  </div>
</template>

<script setup lang="ts">
import { defineProps, withDefaults, ref } from "vue";
import type { HTMLAttributes } from 'vue';
import { Edit, ChevronDown, ChevronUp } from "lucide-vue-next";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import  { type MenuButtonVariants, menuButtonVariants} from ".";
import { cn } from '@/lib/utils'

interface MenuButtonOption {
  label: string;
  href: string;
  description?: string;
}

interface MenuButtonProps {
  mainText?: string;
  mainHref?: string;
  options?: MenuButtonOption[];
  variant?: MenuButtonVariants['variant'];
  size?: MenuButtonVariants['size'];
  class?: HTMLAttributes['class'];
}

const props = withDefaults(defineProps<MenuButtonProps>(), {
  mainText: "Click me",
  mainHref: "/",
  options: (): MenuButtonOption[] => [],
  class: '',
  size: 'sm',
  variant: 'outline',
});

const isDropdownOpen = ref(false);

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
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
      :class="
        cn('rounded-r-none border-r-0 flex items-center justify-start', props.class)
      "
    >
      <router-link :to="mainHref" class="flex items-center w-full">
        <Edit class="mr-2 h-4 w-4" />
        {{ mainText }}
      </router-link>
    </Button>

    <DropdownMenu>
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
      <DropdownMenuContent @close="isDropdownOpen = false">
        <DropdownMenuItem v-for="(option, index) in options" :key="index" asChild>
            <div class="flex items-center justify-start">
          <router-link :to="option.href" class="flex flex-col items-start">
              <span class="text-left">{{ option.label }}</span>
              <span v-if="option.description" class="text-sm text-gray-500 text-left">{{ option.description }}</span>
          </router-link>
            </div>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  </div>
</template>

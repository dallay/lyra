<script setup lang="ts">
import { cn } from '@/lib/utils';
import type { LinkProp } from '~/components/nav/links-list';

interface NavLinkContentProps {
  icon: string;
  title: string;
  label: string;
  isCollapsed: boolean;
  isExpanded?: boolean;
  variant: LinkProp['variant'];
}

const props = defineProps<NavLinkContentProps>();
</script>

<template>
  <Icon v-if="icon" :name="icon" color="black" :class="cn('size-4', !isCollapsed && 'mx-2')"/>
  <template v-if="!isCollapsed">
    {{ title }}
    <span v-if="label"
          :class="cn('ml-auto', variant === 'default' && 'text-background dark:text-white')">
          {{ label }}
        </span>
  </template>
</template>

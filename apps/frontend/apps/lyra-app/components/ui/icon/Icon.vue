<template>
  <Suspense>
    <component
      v-if="IconComponent"
      :is="IconComponent"
      :class="cn('w-4 h-4', props.class)"
      :stroke-width="strokeWidth || 2.5"
    />

    <Icon
      v-else
      :name="name"
      :class="cn('w-4 h-4', props.class)"
      :strokeWidth="strokeWidth || 2.5"
    />
    <template #fallback>
      <LoaderCircle class="mr-1 size-4 text-muted-foreground animate-spin" />
    </template>
  </Suspense>
</template>

<script setup lang="ts">
import { computed, defineProps,type HTMLAttributes } from 'vue';
import { icons, LoaderCircle } from 'lucide-vue-next';
import { cn } from '@/lib/utils';
import type {IconType} from "~/components/ui/icon/types";


interface IconProps {
  name: IconType;
  class?: HTMLAttributes['class']
  strokeWidth?: number;
}

const props = defineProps<IconProps>();

const IconComponent = computed(() => {
  return typeof props.name === 'string' && props.name in icons
    ? icons[props.name as keyof typeof icons]
    : null;
});
</script>

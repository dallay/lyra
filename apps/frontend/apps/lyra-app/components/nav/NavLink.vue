<script setup lang="ts">
import type {LinkProp} from "./links-list";
import {computed, defineProps, ref, withDefaults} from 'vue';
import {cn} from '@/lib/utils';
import {buttonVariants} from '@/components/ui/button';
import {Collapsible, CollapsibleContent, CollapsibleTrigger} from '@/components/ui/collapsible';
import {useRoute} from 'vue-router';
import NavLinkContent from "~/components/nav/NavLinkContent.vue";
import { usePermissionCheck } from '@/composables/usePermissionCheck';

const props = withDefaults(
  defineProps<{
    icon?: LinkProp['icon'];
    title?: LinkProp['title'];
    to?: LinkProp['to'];
    sub?: LinkProp[];
    label?: LinkProp['label'];
    variant?: LinkProp['variant'];
    level?: number;
    open?: boolean;
    isCollapsed?: boolean;
  }>(),
  {
    icon: 'lucide:file',
    title: '',
    to: '',
    label: '',
    variant: 'ghost',
    sub: () => [],
    level: 0,
    open: false,
    isCollapsed: false,
  }
);

const route = useRoute();
const isExpanded = ref(props.open);
const { hasPermission } = usePermissionCheck(props.to);

const isActiveLink = computed(() => {
  if (!props.to) return false;
  return route.path === props.to || route.path.startsWith(props.to);
});

const computedClasses = computed(() => {
  const baseClasses = cn(
    buttonVariants({variant: props.variant, size: props.isCollapsed ? 'icon' : 'sm'}),
    'justify-start flex items-center'
  );

  const variantClasses = props.variant === 'default'
    ? 'dark:bg-muted dark:text-white dark:hover:bg-muted dark:hover:text-white'
    : '';

  const stateClasses = isActiveLink.value
    ? 'font-bold text-primary-500 dark:text-primary-200 bg-muted'
    : '';


  const collapsedClasses = props.isCollapsed
    ? 'h-9 w-9 justify-center space-y-2'
    : '';

  return `${baseClasses} ${variantClasses} ${stateClasses} ${collapsedClasses}`.trim();
});

const paddingLeftStyle = computed(() => {
  return !props.isCollapsed ? {paddingLeft: `${props.level}rem`} : undefined;
});
</script>

<template>
  <span v-if="isCollapsed" class="sr-only">{{ title }}</span>
  <span v-if="!hasPermission" class="hidden"></span>
  <template v-else>
    <template v-if="sub.length > 0">
      <Collapsible v-model:open="isExpanded">
        <CollapsibleTrigger class="w-full">
          <div :class="computedClasses" :style="paddingLeftStyle">
            <NavLinkContent :icon="icon" :title="title"
                             :label="label" :isCollapsed="isCollapsed" :variant="variant"
            />
            <Icon v-if="!isCollapsed"
                  :name="isExpanded ? 'lucide:chevron-up' : 'lucide:chevron-down'" color="black"
                  :class="cn('ml-auto size-4', label && 'ml-2')"/>
          </div>
        </CollapsibleTrigger>
        <CollapsibleContent v-show="isExpanded">
          <template v-for="(link, index) in sub" :key="index">
            <NavLink
              v-bind="link"
              :level="level + 1"
              :isCollapsed="isCollapsed"
            />
          </template>
        </CollapsibleContent>
      </Collapsible>
    </template>

    <NuxtLink
      v-else-if="to"
      :to="to"
      :class="computedClasses"
      :style="paddingLeftStyle"
    >
      <NavLinkContent :icon="icon" :title="title"
                       :label="label" :isCollapsed="isCollapsed" :variant="variant"
      />
    </NuxtLink>

    <div v-else :class="computedClasses" :style="paddingLeftStyle">
      <NavLinkContent :icon="icon" :title="title"
                        :label="label" :isCollapsed="isCollapsed" :variant="variant"
      />
    </div>
  </template>
</template>

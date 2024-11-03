<template>
  <TooltipProvider>
    <Tooltip :defaultOpen="false">
      <TooltipTrigger>
        <Toggle
          :aria-label="label"
          :default-value="isActiveToggle"
          role="button"
          v-model:pressed="pressed"
          @click="handleClick"
          class="flex items-center gap-0.5"
          :disabled="isDisabled"
        >
          <Icon :name="icon" />
          <span class="sr-only">{{ label }}</span>
          <slot name="icon-after" />
        </Toggle>
      </TooltipTrigger>
      <TooltipContent side="top">
        <span class="flex items-center gap-0.5">
          {{ label }}
          <ShortcutKey
            v-if="keyboardShortcut.length"
            v-for="shortcutKey in keyboardShortcut"
            :key="shortcutKey"
            :children="shortcutKey"
          />
        </span>
      </TooltipContent>
    </Tooltip>
  </TooltipProvider>
</template>

<script setup lang="ts">
import { defineProps, computed, withDefaults, ref, watch, defineEmits } from 'vue';
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';
import { Icon } from '@/components/ui/icon';
import { Toggle } from '~/components/ui/toggle';
import ShortcutKey from './ShortcutKey.vue';

export interface MenuButtonProps {
    icon: string;
    label: string;
    isActive?: boolean | (() => boolean);
    disabled?: boolean | (() => boolean);
    shortcut?: string[] | (() => string[]);
}

const props = withDefaults(defineProps<MenuButtonProps>(), {
    isActive: true,
  disabled: false,
});

const isActiveToggle = computed(() => {
  return typeof props.isActive === 'function' ? props.isActive() : props.isActive;
});

const isDisabled = computed(() => {
  return typeof props.disabled === 'function' ? props.disabled() : props.disabled;
});

const pressed = ref(isActiveToggle.value);

const emit = defineEmits(['update:pressed', 'click']);

const updatePressed = (value: boolean) => {
    emit('update:pressed', value);
};

const handleClick = () => {
    emit('click');
};

watch(
    () => props.isActive,
    (newVal) => {
    pressed.value = typeof newVal === 'function' ? newVal() : newVal;
    },
    { immediate: true },
);

const keyboardShortcut = computed(() => {
    const shortcut = typeof props.shortcut === 'function' ? props.shortcut() : (props.shortcut ?? []);
    return shortcut;
});
</script>

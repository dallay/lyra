<template>
  <div class="flex gap-2">
    <MenuButton
      v-for="(button, index) in props.buttons"
      :key="`menu-button-${button.label}-${index}`"
      :icon="button.icon"
      :label="button.label"
      :isActive="button.isActive || false"
      :shortcut="button.shortcut"
      :onClick="button.onClick"
    />
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import MenuButton, { type MenuButtonProps } from './MenuButton.vue';

type ActionableMenuButton = MenuButtonProps & { onClick: () => void };

export interface MenuButtonGroupProps {
	buttons: ActionableMenuButton[];
}

const props = defineProps<MenuButtonGroupProps>();
</script>

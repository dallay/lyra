<template>
  <div class="flex gap-2">
    <MenuButton
      v-for="(button, index) in buttons"
      :key="index"
      :icon="button.icon"
      :label="button.label"
      :isActive="button.isActive"
      :shortcut="button.shortcut"
      :onClick="button.onClick"
    />
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";
import MenuButton, { type MenuButtonProps } from "./MenuButton.vue";

const props = defineProps<{ buttons: MenuButtonProps[] }>();
</script>

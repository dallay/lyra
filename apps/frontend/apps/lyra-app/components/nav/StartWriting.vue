<script setup lang="ts">
import MenuButton from "~/components/ui/button/menu-button/MenuButton.vue";
const writeOption = [
  {
    label: "Blank draft",
    href: "/new-post",
    description: "Create a blank draft from scratch",
  },
  {
    label: "Use template",
    href: "/new-template",
    description: "Create a draft using a custom template",
  },
];
</script>

<template>
  <MenuButton
    mainText="Start Writing"
    mainHref="/write"
    :options="writeOption"
    variant="outline"
  />
</template>

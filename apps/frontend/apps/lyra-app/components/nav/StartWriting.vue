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

const identifier = computed(() => {
  return crypto.randomUUID();
});
</script>

<template>
  <MenuButton
    mainText="Start Writing"
    :mainHref="`/posts/${identifier}/edit`"
    :options="writeOption"
    variant="outline"
  />
</template>

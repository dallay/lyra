<template>
  <kbd
    :class="
      cn(
        'inline-flex items-center justify-center w-5 h-5 p-1 text-[0.625rem] rounded font-semibold leading-none border border-neutral-200 text-neutral-500 border-b-2',
        props.class
      )
    "
    >{{ displayKey }}</kbd
  >
</template>

<script setup lang="ts">
import { computed, defineProps, type HTMLAttributes } from "vue";
import { cn } from "~/lib/utils";

interface ShortcutKeyProps {
  children: string;
  class?: HTMLAttributes["class"];
}

const props = defineProps<ShortcutKeyProps>();

const isMac =
  typeof window !== "undefined" &&
  navigator.platform.toUpperCase().includes("MAC");

const displayKey = computed(() => {
  if (props.children === "Mod") {
    return isMac ? "⌘" : "Ctrl";
  }
  if (props.children === "Shift") {
    return "⇧";
  }
  if (props.children === "Alt") {
    return isMac ? "⌥" : "Alt";
  }
  return props.children;
});
</script>

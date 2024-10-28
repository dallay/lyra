<script setup lang="ts">
import { defineProps, type HTMLAttributes } from "vue";
import { cn } from "@/lib/utils";

interface SpinnerProps {
  class?: HTMLAttributes["class"];
}

const props = defineProps<SpinnerProps>();
</script>

<template>
  <div
    :class="
      cn(
        'animate-spin rounded-full border-2 border-current border-t-transparent h-4 w-4',
        props.class
      )
    "
  />
</template>

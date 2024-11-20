<template>
  <div class="flex items-center gap-1 w-full p-0.5">
    <div class="relative w-full max-w-sm items-center">
      <Input
        v-model="url"
        type="text"
        class="border rounded p-1 w-full min-w-52 pl-10"
        placeholder="Enter new URL"
      />
      <span
        class="absolute start-0 inset-y-0 flex items-center justify-center px-2"
      >
        <Icon name="lucide:link" />
      </span>
    </div>
    <Button variant="ghost" class="flex" @click="onConfirm">
      <Icon name="lucide:check" />
      <span class="sr-only">Confirm</span>
    </Button>
    <Button variant="ghost" class="flex" @click="onCancel">
      <Icon name="lucide:x" />
      <span class="sr-only">Cancel</span>
    </Button>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, ref, watch } from "vue";
import { Button } from "@/components/ui/button";
import { Icon } from "@/components/ui/icon";
import { Input } from "@/components/ui/input";

const props = defineProps<{ initialUrl?: string }>();
const emit = defineEmits<{
  (e: "confirm", url: string): void;
  (e: "cancel"): void;
}>();

const url = ref(props.initialUrl ?? "");

watch(
  () => props.initialUrl,
  (newUrl) => {
    url.value = newUrl ?? "";
  }
);

const onConfirm = () => emit("confirm", url.value);
const onCancel = () => emit("cancel");
</script>

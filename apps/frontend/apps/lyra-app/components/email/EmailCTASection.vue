<script setup lang="ts">
import { ref, watch } from 'vue';
import type { HTMLAttributes } from 'vue';

// Define the props with default values
const props = withDefaults(
  defineProps<{
    class?: HTMLAttributes['class'];
    placeholder?: string;
    description?: string;
    header?: string;
    buttonText?: string;
    buttonColor?: string;
    buttonTextColor?: string;
    backgroundColor?: string;
    textColor?: string;
  }>(),
  {
    class: 'w-full max-w-md space-y-2 flex flex-col items-center justify-center',
    placeholder: 'Enter your email',
    description: 'Subscribe to our newsletter to get the latest updates.',
    header: 'Subscribe to our newsletter',
    buttonText: 'subscribe',
    buttonColor: '#374151',
    buttonTextColor: '#F3F4F6',
    backgroundColor: '#111827',
    textColor: '#9BA3AF',
  }
);

// biome-ignore lint/correctness/noUnusedVariables: Biome needs support for Vue/Nuxt.
const identifier = ref(crypto.randomUUID());

// Watch for changes in props (if necessary)
watch(
  () => props,
  (newProps) => {
    // Handle any specific logic if needed when props change
    console.log('Props updated:', newProps);
  },
  { deep: true }
);
</script>

<template>
  <section class="m-1 flex flex-col items-center justify-center p-2">
    <div
      :style="{
        backgroundColor: props.backgroundColor,
        borderColor: props.backgroundColor
      }"
      class="relative w-full rounded-lg border p-4 text-center shadow"
    >
      <h5 :style="{ color: props.textColor }" class="mb-2 text-3xl font-bold">
        {{ props.header }}
      </h5>
      <p :style="{ color: props.textColor, filter: 'brightness(80%)' }" class="mb-5 text-base sm:text-lg">
        {{ props.description }}
      </p>
      <div class="flex items-center justify-center space-y-4 sm:space-x-4 sm:space-y-0">
        <EmailCTA
          :id="identifier"
          :placeholder="props.placeholder"
          :buttonText="props.buttonText"
          :buttonColor="props.buttonColor"
          :buttonTextColor="props.buttonTextColor"
        />
      </div>
    </div>
  </section>
</template>

<!-- components/ConfettiTrigger.vue -->
<template>
  <div ref="triggerEl" @click="triggerConfetti">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useScriptNpm } from '#imports';

export interface JSConfettiApi {
  JSConfetti: {
    new (): {
      addConfetti: (options?: { emojis: string[] }) => void;
    };
  };
}

declare global {
  interface Window extends JSConfettiApi {}
}

const triggerEl = ref<HTMLElement>();

// Load the js-confetti script
const { $script } = useScriptNpm<JSConfettiApi>({
  packageName: 'js-confetti',
  file: 'dist/js-confetti.browser.js',
  version: '0.12.0',
  scriptOptions: {
    use() {
      return { JSConfetti: window.JSConfetti };
    },
    bundle: true,
  },
});

const triggerConfetti = () => {
  $script.then(({ JSConfetti }) => {
    const confetti = new JSConfetti();
    confetti.addConfetti({ emojis: ['🌈', '⚡️', '💥', '✨', '💫', '🌸'] });
  });
};
</script>

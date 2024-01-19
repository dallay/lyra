<script setup lang="ts">
import { ref } from 'vue';

const count = ref(0);
</script>

<template>
  <button
    type="button"
    class="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-700"
    @click="count++"
  >
    count is {{ count }}
  </button>
</template>

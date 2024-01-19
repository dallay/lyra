<script setup lang="ts">
import { ref, onMounted } from 'vue';

const count = ref(0);

onMounted(() => {
  console.log('mounted');
  //get from localStorage
  const storedCount = localStorage.getItem('count');
  if (storedCount) {
    count.value = parseInt(storedCount);
  }
});

const increment = () => {
  console.log('increment');
  count.value++;
  //store on localStorage
  localStorage.setItem('count', count.value.toString());
};
</script>

<template>
  <button
    type="button"
    class="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-700"
    @click="increment"
  >
    count is {{ count }}
  </button>
</template>

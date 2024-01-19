<script setup lang="ts">
import { ref } from 'vue';

const count = ref(0);
</script>
<template>
	<button
		type="button"
		class="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-700"
		@click="count++"
	>
		example count is {{ count }}
	</button>
</template>

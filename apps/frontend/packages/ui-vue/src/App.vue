<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import { loadTheme } from '@lyra/vm-core';
import { onMounted } from 'vue';

onMounted(() => {
	loadTheme();
});
</script>

<template>
	<header class="PrincipalHeader">
		<img alt="Vue logo" class="logo" src="/vue-ui.svg" width="125" height="125" />

		<div class="flex items-center justify-center">
			<nav class="nav-links flex items-center justify-around">
				<RouterLink to="/">Home</RouterLink>
				<RouterLink to="/about">About</RouterLink>
				<RouterLink to="/table">Table</RouterLink>
			</nav>
		</div>
	</header>
	<body>
		<RouterView />
	</body>
</template>

<style scoped>
.PrincipalHeader {
	@apply bg-tertiary-100 dark:bg-tertiary-950 text-tertiary-900 dark:text-tertiary-200;
}
body {
	@apply bg-tertiary-100 dark:bg-tertiary-950 text-tertiary-900 dark:text-tertiary-200
  scrollbar scrollbar-w-1 scrollbar-thumb-rounded-[0.25rem]
  scrollbar-track-slate-200  scrollbar-thumb-gray-400 dark:scrollbar-track-gray-900 dark:scrollbar-thumb-gray-700;
}
.nav-links {
	@apply text-tertiary-500 dark:text-tertiary-300 space-x-4 text-lg font-bold uppercase tracking-wider;
}
</style>

<script setup lang="ts">
import { defineAsyncComponent, computed, ref } from 'vue';

const props = defineProps({
	name: {
		type: String,
		required: true,
	},
});

const dynamicComponent = computed(() =>
	defineAsyncComponent(() => import(`./icons/${props.name}.vue`))
);

const key = ref(crypto.randomUUID());
</script>

<template>
	<component :is="dynamicComponent" :key="key" />
</template>

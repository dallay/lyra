<script setup lang="ts">
import { defineAsyncComponent, computed, ref } from 'vue';

export interface SvgIconProps {
	name: string;
}

const props = defineProps<SvgIconProps>();

const dynamicComponent = computed(() =>
	defineAsyncComponent(() => import(`./icons/${props.name}.vue`))
);

const key = ref(crypto.randomUUID());
</script>

<template>
	<component :is="dynamicComponent" :key="key" />
</template>

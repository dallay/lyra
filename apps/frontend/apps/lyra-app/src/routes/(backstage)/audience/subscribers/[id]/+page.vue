<script setup lang="ts">
import { useRoute } from 'vue-router';

const route = useRoute();
</script>

<template>
	<h1>Custom Subscriber View</h1>
	{{ route.params.id }}
</template>

<style scoped lang="scss"></style>

<script lang="ts" setup>
defineProps<{
	label?: string;
	required?: boolean;
	invalid?: boolean | string;
	help?: string;
}>();

const uid = crypto.randomUUID();
</script>

<template>
	<div class="flex flex-col">
		<label :for="uid" class="mb-2 flex items-center text-sm font-bold empty:hidden">
			<template v-if="label">{{ label }}</template>
			<span v-if="required" class="text-red-500">*</span>
			<slot name="label"></slot>
		</label>

		<div class="min-h-38px flex items-center">
			<slot :uid="uid" :invalid="!!invalid"></slot>
		</div>

		<div v-if="invalid && typeof invalid === 'string'" class="mt-1 text-xs text-red-500">
			{{ invalid }}
		</div>

		<div
			v-if="!(invalid && typeof invalid === 'string') && help"
			class="mt-1 text-xs text-gray-500 dark:text-gray-400"
		>
			{{ help }}
		</div>
	</div>
</template>

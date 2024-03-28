<script lang="ts" setup>
import { IMaskDirective as vImask } from 'vue-imask';

import FormControl from '../form-control/FormControl.vue';

const maskedModel = defineModel<string>('masked');
const unmaskedModel = defineModel<string | number>('unmasked');

defineProps<{
	mask?: object;
	label?: string;
	required?: boolean;
	disabled?: boolean;
	invalid?: boolean | string;
	help?: string;
}>();

function onAccept(evt: CustomEvent) {
	const maskRef = evt.detail;
	maskedModel.value = maskRef.value;
	unmaskedModel.value = maskRef.unmaskedValue || undefined;
}
</script>

<template>
	<FormControl v-slot="{ uid }" :label="label" :required="required" :invalid="invalid" :help="help">
		<input
			:id="uid"
			v-imask="mask"
			v-bind="$attrs"
			:value="masked"
			:disabled="disabled"
			class="InputMask-Input"
			:class="{ invalid, disabled }"
			type="text"
			autocomplete="off"
			@accept="onAccept"
		/>
	</FormControl>
</template>

<style lang="scss" scoped>
.InputMask-Input {
	@apply z-2 w-full rounded border border-slate-400 px-3 py-2;
	@apply bg-white leading-tight dark:bg-slate-800;
	@apply placeholder:text-slate-400 dark:placeholder:text-slate-500;
	@apply focus:ring-primary-500/50 focus:border-primary-400 focus:outline-none focus:ring-2;

	&.invalid {
		@apply border-red-500;
		@apply focus:border-red-500 focus:ring-red-500/50;
	}

	&.disabled {
		@apply cursor-not-allowed;
	}
}
</style>

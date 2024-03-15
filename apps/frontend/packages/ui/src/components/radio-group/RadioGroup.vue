<script lang="ts" setup>
import FormControl from '../form-control/FormControl.vue';

defineOptions({
	inheritAttrs: false,
});

const valueModel = defineModel<unknown>('value');

defineProps<{
	label?: string;
	options?: string[] | { label: string; value: unknown; [key: string]: unknown }[];
	disabled?: boolean;
	required?: boolean;
	invalid?: boolean | string;
	help?: string;
}>();

const emit = defineEmits<{
	(evt: 'change', val: unknown): void;
}>();
</script>

<template>
	<FormControl v-slot="{ uid }" :label="label" :required="required" :invalid="invalid" :help="help">
		<div class="flex items-center gap-4">
			<label
				v-for="(item, index) in options"
				:key="index"
				class="flex items-center"
				:class="[disabled ? 'cursor-not-allowed opacity-60' : 'cursor-pointer']"
			>
				<div class="relative flex items-center justify-center">
					<input
						:id="`${uid}-${index}`"
						v-model="valueModel"
						v-bind="$attrs"
						type="radio"
						:name="uid"
						:value="typeof item === 'object' ? item.value : item"
						:disabled="disabled"
						class="radio"
						:class="{ invalid }"
						@change="emit('change', valueModel)"
					/>

					<div
						class="text-primary-500 absolute h-3.5 w-3.5 select-none"
						:class="{
							'i-mdi-circle': valueModel === (typeof item === 'object' ? item.value : item),
						}"
					></div>
				</div>

				<div class="ml-2">
					{{ typeof item === 'object' ? item.label : item }}
				</div>
			</label>
		</div>
	</FormControl>
</template>

<style lang="scss" scoped>
.radio {
	@apply h-5 w-5 appearance-none rounded-full border border-slate-400 bg-white dark:border-slate-600;
	@apply focus:ring-primary-400 focus:shadow-lg focus:outline-none focus:ring-2;

	&.invalid {
		@apply border-red-500 dark:border-red-500;
		@apply focus:border-red-500 focus:ring-red-500;
	}
}
</style>

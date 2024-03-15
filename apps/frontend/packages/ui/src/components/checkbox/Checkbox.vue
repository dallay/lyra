<script lang="ts" setup>
import type { InputHTMLAttributes } from 'vue';
import { computed } from 'vue';

interface Props extends /* @vue-ignore */ InputHTMLAttributes {
	value?: boolean;
	checked?: boolean;
	indeterminate?: boolean;
	invalid?: boolean | string;
	disabled?: boolean;
	readonly?: boolean;
}

defineOptions({
	inheritAttrs: false,
});

const props = defineProps<Props>();

const emit = defineEmits<{
	(evt: 'update:value', val: boolean): void;
}>();

const valueModel = computed({
	get: () => props.value || false,
	set: (val) => !props.readonly && emit('update:value', val),
});

const uid = crypto.randomUUID();
</script>

<template>
	<div class="Checkbox-Wrapper">
		<label class="Checkbox-Label" :class="{ disabled, readonly }">
			<div class="Checkbox-Container">
				<input
					:id="uid"
					v-model="valueModel"
					v-bind="$attrs"
					type="checkbox"
					:disabled="disabled"
					:readonly="readonly"
					class="Checkbox-Input"
					:class="{ hasValue: value || checked || indeterminate, invalid }"
				/>

				<div
					class="Checkbox-Icon"
					:class="[
						indeterminate
							? 'i-material-symbols-check-indeterminate-small-rounded'
							: value || checked
								? 'i-material-symbols-check-small-rounded'
								: '',
					]"
				></div>
			</div>

			<div class="Checkbox-Text">
				<slot></slot>
			</div>
		</label>

		<div v-if="typeof invalid === 'string' && invalid" class="mt-1 text-xs text-red-500">
			{{ invalid }}
		</div>
	</div>
</template>

<style lang="scss" scoped>
.Checkbox-Wrapper {
	@apply inline-flex flex-col;
}

.Checkbox-Label {
	@apply min-h-38px flex cursor-pointer items-center;

	&.disabled {
		@apply cursor-not-allowed opacity-60;
	}

	&.readonly {
		@apply cursor-default;
	}
}

.Checkbox-Container {
	@apply relative flex items-center justify-center;
}

.Checkbox-Input {
	@apply h-5 w-5 appearance-none overflow-hidden rounded border border-slate-400 dark:border-slate-600;
	@apply focus:ring-primary-500/50 focus:shadow-lg focus:outline-none focus:ring-2;
	@apply bg-white;

	&.hasValue {
		@apply bg-primary-500;
	}

	&.invalid {
		@apply border-red-500 dark:border-red-500;
		@apply focus:border-red-500 focus:ring-red-500/50;
	}
}

.Checkbox-Icon {
	@apply absolute h-6 w-6 select-none text-white;
}

.Checkbox-Text {
	@apply ml-2 empty:hidden;
}
</style>

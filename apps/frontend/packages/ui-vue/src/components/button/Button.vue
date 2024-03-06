<script lang="ts" setup>
import type { ButtonHTMLAttributes, Ref, WritableComputedRef } from 'vue';
import { ref, computed, inject, onMounted } from 'vue';

import Spinner from '../spinner/Spinner.vue';

interface Props extends /* @vue-ignore */ ButtonHTMLAttributes {
	label?: string;
	icon?: string;
	variant?: 'contained' | 'outlined' | 'text';
	color?: 'primary' | 'secondary' | 'success' | 'danger' | 'warning' | 'info';
	size?: 'small' | 'large';
	prepend?: string;
	append?: string;
	loading?: boolean;
	disabled?: boolean;
}

defineProps<Props>();

const emit = defineEmits<{
	(evt: 'click', val: Event): void;
}>();

const buttonGroup = inject('ButtonGroup', {
	defaultModel: undefined,
	group: undefined,
}) as {
	defaultModel?: WritableComputedRef<number | undefined>;
	group?: Ref<HTMLDivElement | undefined>;
};

const idx = ref(-1);
const self = ref<HTMLButtonElement>();

onMounted(() => {
	if (buttonGroup.group?.value && self.value) {
		idx.value = Array.from(buttonGroup.group.value.children).indexOf(self.value);
	}
});

const hasGroup = computed(() => typeof buttonGroup.defaultModel?.value === 'number');

function onClick(evt: Event) {
	emit('click', evt);

	if (hasGroup.value) {
		if (typeof buttonGroup.defaultModel?.value === 'number') {
			buttonGroup.defaultModel.value = idx.value;
		}
	}
}
</script>

<template>
	<button
		ref="self"
		v-bind="$attrs"
		type="button"
		class="Button"
		:class="{
			contained: hasGroup
				? idx === buttonGroup.defaultModel?.value
				: variant === 'contained' || !variant,
			outlined: hasGroup ? idx !== buttonGroup.defaultModel?.value : variant === 'outlined',
			text: variant === 'text',
			primary: color === 'primary' || !color,
			secondary: color === 'secondary',
			success: color === 'success',
			danger: color === 'danger',
			warning: color === 'warning',
			info: color === 'info',
			small: size === 'small',
			large: size === 'large',
			icon,
			disabled: disabled || loading,
		}"
		:disabled="disabled || loading"
		@click="onClick"
	>
		<slot>
			<div
				v-if="icon"
				class="Button-Icon"
				:class="[icon, { small: size === 'small', large: size === 'large' }]"
			></div>

			<div
				v-if="prepend"
				class="Button-Icon flex items-center overflow-hidden"
				:class="{ small: size === 'small', large: size === 'large' }"
			>
				<Spinner
					v-if="loading"
					class="Button-Icon"
					:class="{ small: size === 'small', large: size === 'large' }"
				/>
				<div
					v-else
					class="Button-Icon"
					:class="[prepend, { small: size === 'small', large: size === 'large' }]"
				></div>
			</div>

			<div v-if="label">{{ label }}</div>

			<div
				v-if="append"
				class="Button-Icon flex items-center overflow-hidden"
				:class="{ small: size === 'small', large: size === 'large' }"
			>
				<Spinner
					v-if="loading"
					class="Button-Icon"
					:class="{ small: size === 'small', large: size === 'large' }"
				/>
				<div
					v-else
					class="Button-Icon"
					:class="[append, { small: size === 'small', large: size === 'large' }]"
				></div>
			</div>
		</slot>
	</button>
</template>

<style lang="scss" scoped>
.Button {
	@apply flex items-center justify-center gap-2 rounded border px-6 py-2 text-sm font-medium uppercase;
	@apply focus:ring-primary-500/40 focus:outline-none focus:ring-2;

	&.contained {
		@apply text-white shadow-md hover:text-gray-100 hover:shadow-lg active:text-gray-200;

		&.primary {
			@apply bg-primary-500 hover:bg-primary-600 active:bg-primary-700 border-primary-500;
		}

		&.secondary {
			@apply bg-secondary-500 hover:bg-secondary-600 active:bg-secondary-700 border-secondary-500;
		}

		&.success {
			@apply border-green-500 bg-green-500 hover:bg-green-600 active:bg-green-700;
		}

		&.danger {
			@apply border-red-500 bg-red-500 hover:bg-red-600 active:bg-red-700;
		}

		&.warning {
			@apply border-yellow-500 bg-yellow-500 hover:bg-yellow-600 active:bg-yellow-700;
		}

		&.info {
			@apply border-blue-500 bg-blue-500 hover:bg-blue-600 active:bg-blue-700;
		}
	}

	&.outlined {
		@apply border-current bg-transparent;
	}

	&.text {
		@apply border-transparent bg-transparent;
	}

	&.outlined,
	&.text {
		&.primary {
			@apply text-primary-500 hover:text-primary-600 dark:text-primary-400 active:text-primary-700 dark:active:text-primary-300;
			@apply hover:bg-primary-400/25 active:bg-primary-500/25;
		}

		&.secondary {
			@apply text-secondary-500 hover:text-secondary-600 dark:text-secondary-400 active:text-secondary-700 dark:active:text-secondary-300;
			@apply hover:bg-secondary-400/25 active:bg-secondary-500/25;
		}

		&.success {
			@apply text-green-500 hover:text-green-600 active:text-green-700 dark:text-green-400 dark:active:text-green-300;
			@apply hover:bg-green-400/25 active:bg-green-500/25;
		}

		&.danger {
			@apply text-red-500 hover:text-red-600 active:text-red-700 dark:text-red-400 dark:active:text-red-300;
			@apply hover:bg-red-400/25 active:bg-red-500/25;
		}

		&.warning {
			@apply text-yellow-500 hover:text-yellow-600 active:text-yellow-700 dark:text-yellow-400 dark:active:text-yellow-300;
			@apply hover:bg-yellow-400/25 active:bg-yellow-500/25;
		}

		&.info {
			@apply text-blue-500 hover:text-blue-600 active:text-blue-700 dark:text-blue-400 dark:active:text-blue-300;
			@apply hover:bg-blue-400/25 active:bg-blue-500/25;
		}
	}

	&.small {
		@apply px-4 text-xs;
	}

	&.large {
		@apply px-7 text-base;
	}

	&.icon {
		@apply h-10 w-10 overflow-hidden rounded-full p-0;

		&.small {
			@apply h-8 w-8;
		}

		&.large {
			@apply h-11 w-11;
		}
	}

	&.disabled {
		@apply cursor-not-allowed opacity-60;
	}
}

.Button-Icon {
	@apply h-5 w-5;

	&.small {
		@apply h-4 w-4;
	}

	&.large {
		@apply h-6 w-6;
	}
}
</style>

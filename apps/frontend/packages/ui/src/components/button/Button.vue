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
			@apply bg-success-500 hover:bg-success-600 active:bg-success-700 border-success-500;
		}

		&.danger {
			@apply bg-danger-500 hover:bg-danger-600 active:bg-danger-700 border-danger-500;
		}

		&.warning {
			@apply bg-warning-500 hover:bg-warning-600 active:bg-warning-700 border-warning-500;
		}

		&.info {
			@apply bg-info-500 hover:bg-info-600 active:bg-info-700 border-info-500;
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
			@apply text-success-500 hover:text-success-600 dark:text-success-400 active:text-success-700 dark:active:text-success-300;
			@apply hover:bg-success-400/25 active:bg-success-500/25;
		}

		&.danger {
			@apply text-danger-500 hover:text-danger-600 dark:text-danger-400 active:text-danger-700 dark:active:text-danger-300;
			@apply hover:bg-danger-400/25 active:bg-danger-500/25;
		}

		&.warning {
			@apply text-warning-500 hover:text-warning-600 dark:text-warning-400 active:text-warning-700 dark:active:text-warning-300;
			@apply hover:bg-warning-400/25 active:bg-warning-500/25;
		}

		&.info {
			@apply text-info-500 hover:text-info-600 dark:text-info-400 active:text-info-700 dark:active:text-info-300;
			@apply hover:bg-info-400/25 active:bg-info-500/25;
		}
	}

	&.small {
		@apply px-4 text-xs;
	}

	&.large {
		@apply px-7 text-base;
	}

	&.icon {
		@apply w-38px h-38px overflow-hidden rounded-full p-0;

		&.small {
			@apply w-34px h-34px;
		}

		&.large {
			@apply w-42px h-42px;
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

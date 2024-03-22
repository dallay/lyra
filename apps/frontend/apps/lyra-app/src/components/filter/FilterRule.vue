<script setup lang="ts">
import {
	useValdnLocale,
	XButton,
	XCard,
	XDatePicker,
	XPopover,
	XSelect,
	XTextField,
} from '@lyra/ui';
import { computed, onMounted, reactive, toRef } from 'vue';
import { ColumnProperty } from '@/components/filter/Filter';
import {
	filterOperatorFromString,
	getSupportedOperators,
} from '@/components/filter/FilterOperator';
import { useValibotSchema } from 'vue-formor';
import { custom, email, minLength, nullish, object, type Pipe, string, unknown } from 'valibot';

const property = defineModel<ColumnProperty>({
	required: true,
});

const emit = defineEmits<{
	(evt: 'removeFilterProperty', val: ColumnProperty): void;
	(evt: 'applyFilter'): void;
}>();

const valdnLocale = useValdnLocale();

interface BasicForm {
	valueRule?: string | number | Date;
	operator?: string;
}

const flux = reactive({
	filterRuleStatus: false,
});

onMounted(() => {
	if (property.value.availableOperators.length === 0) {
		property.value.availableOperators = getSupportedOperators(property.value.value);
	}
	if (property.value.value === '') {
		flux.filterRuleStatus = true;
	}
});
const state = reactive({
	form: {
		valueRule: property.value.value,
		operator: property.value.operator,
	} as BasicForm,
	valdn: {} as Record<keyof BasicForm, string>,
});
const pipeValidations: Pipe<string> = [minLength(1, valdnLocale.value.required)];
// add email validation if inputType is email
if (property.value.inputType === 'email') {
	pipeValidations.push(email(valdnLocale.value.email));
}
const schema = useValibotSchema(
	computed(() =>
		object({
			valueRule: nullish(unknown(pipeValidations), property.value.value),
			operator: nullish(
				string([
					custom(
						(input) =>
							input !== '' &&
							property.value.availableOperators
								.map((op) => op.value)
								.includes(filterOperatorFromString(input)),
						'Invalid operator'
					),
				]),
				property.value.operator
			),
		})
	),
	toRef(state, 'form'),
	toRef(state, 'valdn')
);
const removeFilterProperty = (column: ColumnProperty) => {
	emit('removeFilterProperty', column);
	flux.filterRuleStatus = false;
};

function getDataTypeFromValue(): 'text' | 'number' | 'date' {
	switch (property.value.value) {
		case 'string':
			return 'text';
		case 'number':
			return 'number';
		case 'date':
			return 'date';
		default:
			return 'text';
	}
}

const inputType = computed(() => {
	if (property.value.inputType) {
		return property.value.inputType;
	}
	return getDataTypeFromValue();
});
const formatProperty = (value: unknown) => {
	if (inputType.value === 'date') {
		return new Date(value as Date).toLocaleDateString();
	}
	return value;
};
const submit = () => {
	if (schema.validate()) {
		property.value.value = state.form.valueRule;
		property.value.operator = filterOperatorFromString(state.form.operator ?? '');
		flux.filterRuleStatus = false;

		emit('applyFilter');
	}
};
</script>

<template>
	<XPopover v-model="flux.filterRuleStatus">
		<span
			class="bg-secondary-100 text-secondary-800 dark:bg-tertiary-700 dark:text-secondary-400 border-secondary-400/50 inline-flex cursor-pointer items-center rounded-full border px-2.5 py-0.5 text-xs font-medium"
			@click="flux.filterRuleStatus = !flux.filterRuleStatus"
		>
			<i :class="`${property.icon} mr-1 h-5 w-5`"></i>
			{{ property.name }}={{ property.operator }}:{{ formatProperty(property.value) }}
			<i
				class="i-material-symbols:close ml-1 h-5 w-5 cursor-pointer"
				@click="removeFilterProperty(property)"
			></i>
		</span>
		<template #content>
			<XCard>
				<template #header>
					<header class="flex items-center justify-start p-2">
						<label :for="property.key" class="text-center text-base">
							<i v-if="property.icon" :class="`${property.icon} mr-1 h-5 w-5`"></i>
							{{ property.name }}
						</label>
						<XSelect
							:key="property.operator"
							v-model:value="state.form.operator"
							class="mx-1 flex-1"
							clearable
							use-parent-offset
							:options="property.availableOperators"
							:invalid="state.valdn.operator"
						/>
						<i
							class="i-material-symbols:cancel-outline-rounded ml-1 h-5 w-5 cursor-pointer"
							@click="removeFilterProperty(property)"
						></i>
					</header>
				</template>
				<div class="flex items-center justify-center">
					<XTextField
						v-if="inputType !== 'date' && inputType !== 'select' && inputType !== 'checkbox'"
						v-model:value="state.form.valueRule as string | number"
						:placeholder="property.name"
						:type="inputType"
						:prepend="property.icon"
						:invalid="state.valdn.valueRule"
						clearable
					/>
					<XSelect
						v-else-if="inputType === 'select'"
						v-model:value="state.form.valueRule as string | number"
						:options="property.options"
						:invalid="state.valdn.valueRule"
						clearable
						use-parent-offset
					/>
					<XDatePicker
						v-else-if="inputType === 'date'"
						v-model:value="state.form.valueRule as string"
						clearable
						use-parent-offset
					>
					</XDatePicker>
				</div>

				<template #footer>
					<div class="flex justify-end p-2">
						<XButton
							size="small"
							variant="outlined"
							icon="i-material-symbols:filter-alt"
							class="flex-1"
							@click="submit"
							>Apply
						</XButton>
					</div>
				</template>
			</XCard>
		</template>
	</XPopover>
</template>

<style scoped lang="scss"></style>

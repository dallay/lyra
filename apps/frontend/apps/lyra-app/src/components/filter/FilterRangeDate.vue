<script setup lang="ts">
import { onMounted, reactive, watch } from 'vue';
import { XButton, XDateRangePicker, XCheckbox } from '@lyra/ui';
import { format, startOfMonth, startOfYear, subDays, subMonths, subYears } from 'date-fns';

const startDate = defineModel<string>('startDate', {
	default: '',
});
const endDate = defineModel<string>('endDate', {
	default: '',
});
const emit = defineEmits<{
	(event: 'useFilter', value: boolean): void;
	(event: 'applyFilter', value: { startDate: string; endDate: string }): void;
}>();

const flux = reactive({
	allHistorical: ['7D', '14D', 'MTD', '1M', '3M', '6M', 'YTD', '1Y'],
	enableFilter: false,
});

function setHistorical(historical: string) {
	const today = new Date();

	endDate.value = format(subDays(today, 1), 'yyyy/MM/dd');

	if (historical === '7D') {
		startDate.value = format(subDays(today, 7), 'yyyy/MM/dd');
	}

	if (historical === '14D') {
		startDate.value = format(subDays(today, 14), 'yyyy/MM/dd');
	}

	if (historical === 'MTD') {
		startDate.value = format(startOfMonth(today), 'yyyy/MM/dd');
	}

	if (historical === '1M') {
		startDate.value = format(subMonths(today, 1), 'yyyy/MM/dd');
	}

	if (historical === '3M') {
		startDate.value = format(subMonths(today, 3), 'yyyy/MM/dd');
	}

	if (historical === '6M') {
		startDate.value = format(subMonths(today, 6), 'yyyy/MM/dd');
	}

	if (historical === 'YTD') {
		startDate.value = format(startOfYear(today), 'yyyy/MM/dd');
	}

	if (historical === '1Y') {
		startDate.value = format(subYears(today, 1), 'yyyy/MM/dd');
	}
}
watch(
	() => flux.enableFilter,
	(value) => {
		emit('useFilter', value);
		if (value && startDate.value === '' && endDate.value === '') {
			setHistorical('1Y');
		}
	}
);
onMounted(() => {
	if (flux.enableFilter) setHistorical('1Y');
});
const applyFilter = () => {
	emit('applyFilter', { startDate: startDate.value, endDate: endDate.value });
};
</script>

<template>
	<div class="flex w-1/2 items-center justify-around">
		<XCheckbox v-model="flux.enableFilter" class="mx-1">Use Date Range Filter</XCheckbox>
		<XDateRangePicker
			v-model:startValue="startDate"
			v-model:endValue="endDate"
			:maxDate="subDays(new Date(), 1)"
			clearable
			:disabled="!flux.enableFilter"
			data-testid="historical-date-range-picker"
			@hide="applyFilter"
		>
			<template #panel>
				<div class="mt-1 border-t border-gray-200 dark:border-gray-700">
					<div class="my-1 text-sm font-medium">Historical</div>

					<div class="grid grid-cols-4 gap-1">
						<XButton
							v-for="historical in flux.allHistorical"
							:key="historical"
							:data-testid="historical"
							:label="historical"
							variant="text"
							color="secondary"
							size="small"
							class="!px-0 !py-1"
							@click="setHistorical(historical)"
						/>
					</div>
				</div>
			</template>
		</XDateRangePicker>
	</div>
</template>

<style scoped lang="scss"></style>

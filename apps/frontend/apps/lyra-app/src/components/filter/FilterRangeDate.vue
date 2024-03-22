<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import { XButton, XDateRangePicker } from '@lyra/ui';
import { format, startOfMonth, startOfYear, subDays, subMonths, subYears } from 'date-fns';

const startDate = defineModel<string>('startDate', {
	default: '',
});
const endDate = defineModel<string>('endDate', {
	default: '',
});

const flux = reactive({
	allHistorical: ['7D', '14D', 'MTD', '1M', '3M', '6M', 'YTD', '1Y'],
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

onMounted(() => {
	setHistorical('1Y');
});
</script>

<template>
	<div class="flex w-1/3 justify-end">
		<XDateRangePicker
			v-model:startValue="startDate"
			v-model:endValue="endDate"
			:maxDate="subDays(new Date(), 1)"
			clearable
		>
			<template #panel>
				<div class="mt-1 border-t border-gray-200 dark:border-gray-700">
					<div class="my-1 text-sm font-medium">Historical</div>

					<div class="grid grid-cols-4 gap-1">
						<XButton
							v-for="historical in flux.allHistorical"
							:key="historical"
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

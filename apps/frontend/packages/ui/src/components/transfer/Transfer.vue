<script lang="ts" setup>
import { ref, computed, watch } from 'vue';
import Draggable from 'vuedraggable';
import { remove } from '@lyra/utilities';

import Checkbox from '../checkbox/Checkbox.vue';
import Button from '../button/Button.vue';

type Item = {
	id?: string | number;
	name?: string;
	checked?: boolean;
};

const sourceModel = defineModel<Item[]>('source', { default: [] });
const targetModel = defineModel<Item[]>('target', { default: [] });

const sourceSelectAll = ref(false);
const sourceIndeterminate = ref(false);
const sourceChecked = computed(() => sourceModel.value.filter((item) => item.checked).length);

const targetSelectAll = ref(false);
const targetIndeterminate = ref(false);
const targetChecked = computed(() => targetModel.value.filter((item) => item.checked).length);

function changeList() {
	sourceModel.value = [...sourceModel.value].map((item) => ({ ...item, checked: false }));
	targetModel.value = [...targetModel.value].map((item) => ({ ...item, checked: false }));
}

function toRight() {
	const source = remove(sourceModel.value, (item: Item) => !!item.checked);

	targetModel.value = [...targetModel.value, ...source].map((item) => ({
		...item,
		checked: false,
	}));
}

function toLeft() {
	const target = remove(targetModel.value, (item: Item) => !!item.checked);

	sourceModel.value = [...sourceModel.value, ...target].map((item) => ({
		...item,
		checked: false,
	}));
}

watch(
	() => sourceSelectAll.value,
	(checked) => {
		sourceModel.value = [...sourceModel.value].map((item) => ({ ...item, checked }));
	}
);

watch(
	() => sourceModel.value,
	(val) => {
		const checked = val.every((item) => item.checked);
		const unchecked = val.every((item) => !item.checked);

		sourceIndeterminate.value = !(checked || unchecked);

		if (checked) sourceSelectAll.value = true;
		if (unchecked) sourceSelectAll.value = false;
	},
	{ deep: true }
);

watch(
	() => targetSelectAll.value,
	(checked) => {
		targetModel.value = [...targetModel.value].map((item) => ({ ...item, checked }));
	}
);

watch(
	() => targetModel.value,
	(val) => {
		const checked = val.every((item) => item.checked);
		const unchecked = val.every((item) => !item.checked);

		targetIndeterminate.value = !(checked || unchecked);

		if (checked) targetSelectAll.value = true;
		if (unchecked) targetSelectAll.value = false;
	},
	{ deep: true }
);
</script>

<template>
	<div class="Transfer">
		<div class="Transfer-Panel">
			<div class="Transfer-PanelTitle">
				<Checkbox v-model:value="sourceSelectAll" :indeterminate="sourceIndeterminate">
					Source List
				</Checkbox>

				<span class="text-sm text-slate-400">{{ sourceChecked }}/{{ sourceModel.length }}</span>
			</div>

			<Draggable
				:list="sourceModel"
				group="list"
				itemKey="name"
				class="Transfer-PanelList"
				style="height: calc(100% - 54px)"
				@change="changeList"
			>
				<template #item="{ element }">
					<div class="Transfer-PanelItem">
						<Checkbox v-model:value="element.checked">{{ element.name }} </Checkbox>
					</div>
				</template>
			</Draggable>
		</div>

		<div class="Transfer-Controller">
			<Button
				:color="sourceChecked ? 'primary' : 'secondary'"
				icon="i-mdi-chevron-right"
				:disabled="!sourceChecked"
				@click="toRight"
			/>
			<Button
				:color="targetChecked ? 'primary' : 'secondary'"
				icon="i-mdi-chevron-left"
				:disabled="!targetChecked"
				@click="toLeft"
			/>
		</div>

		<div class="Transfer-Panel">
			<div class="Transfer-PanelTitle">
				<Checkbox v-model:value="targetSelectAll" :indeterminate="targetIndeterminate">
					Target List
				</Checkbox>

				<span class="text-sm text-slate-400">{{ targetChecked }}/{{ targetModel.length }}</span>
			</div>

			<Draggable
				:list="targetModel"
				group="list"
				itemKey="name"
				class="Transfer-PanelList"
				style="height: calc(100% - 54px)"
				@change="changeList"
			>
				<template #item="{ element }">
					<div class="Transfer-PanelItem">
						<Checkbox v-model:value="element.checked">{{ element.name }}</Checkbox>
					</div>
				</template>
			</Draggable>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.Transfer {
	@apply h-75 flex gap-3;
}

.Transfer-Panel {
	@apply flex-1 overflow-auto rounded-md border border-gray-200 dark:border-gray-700;
}

.Transfer-Controller {
	@apply space-y-2 self-center;
}

.Transfer-PanelTitle {
	@apply z-1 sticky top-0 flex items-center justify-between gap-2 px-3 py-2;
	@apply rounded-t-md bg-gray-200 text-gray-500 dark:bg-gray-700 dark:text-gray-200;
}

.Transfer-PanelList {
	@apply flex flex-col gap-1 bg-white dark:bg-slate-900;
}

.Transfer-PanelItem {
	@apply bg-gray-100 px-3 py-1 dark:bg-slate-800;
}
</style>

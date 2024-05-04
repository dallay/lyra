<script setup lang="ts">
import { type Control, XBreadcrumb, XButton, XTable } from '@lyra/ui';
import { onMounted, onUnmounted, reactive } from 'vue';
import useStore from './store';
import EditorForm from './EditorForm.vue';
import ViewerForm from './ViewerForm.vue';
import { Form } from './types';

const { state, actions, $reset } = useStore();

const defaultControlCursor: Control = {
	paginationType: 'cursor',
	sort: { field: 'createdAt', direction: 'asc' },
	cursor: { cursor: state.cursor, limit: 10 },
};

const flux = reactive({
	columns: [
		{ key: 'name', name: 'Name', sortable: true },
		{ key: 'action', name: 'Action', sortable: false },
	],
	data: state.forms,
	currentForm: {} as Form,
	control: defaultControlCursor as Control,
	loading: state.loading,
	formEditorDrawer: false,
	formViewerDrawer: false,
});

const search = async () => {
	await actions.getForms();
	flux.data = state.forms;
	flux.loading = state.loading;
	flux.control = {
		...flux.control,
		cursor: { cursor: state.cursor, limit: 10 },
	};
};

async function openEditor(id: string) {
	flux.currentForm = await actions.findForm(id);
	flux.formEditorDrawer = true;
}

async function openViewer(id: string) {
	flux.currentForm = await actions.findForm(id);
	flux.formViewerDrawer = true;
}

async function change(params: Control) {
	flux.control = params;
	await search();
}

async function deleteForm(id: string) {
	await actions.deleteForm(id);
	await search();
}

onMounted(async () => {
	await search();
});
onUnmounted(() => {
	$reset();
});
</script>

<template>
	<XBreadcrumb
		:items="[
			{ text: 'Settings', icon: 'i-material-symbols:settings' },
			{ text: 'Publication', icon: 'i-ooui:articles-rtl' },
			{ text: 'Subscriber Form', icon: 'i-material-symbols-light:dynamic-form-rounded' },
		]"
		class="mb-2.5"
	/>
	<h2 class="my-4 text-3xl font-bold">Subscriber Forms</h2>
	<p>Create customizable and embeddable email subscribe forms for your other websites.</p>
	<XTable
		v-model:control="flux.control"
		stickyHeader
		:columns="flux.columns"
		:rows="state.forms"
		@change="change"
	>
		<template #action="{ row }">
			<div class="flex items-center space-x-2 p-2">
				<XButton variant="text" size="small" @click="openViewer(row.id)">View</XButton>
				<XButton variant="text" size="small" @click="openEditor(row.id)">Edit</XButton>
				<EditorForm v-model:form="flux.currentForm" v-model:editor-drawer="flux.formEditorDrawer" />
				<ViewerForm
					v-model:form="flux.currentForm"
					v-model:viewer-drawer="flux.formViewerDrawer"
					@delete="deleteForm"
				/>
			</div>
		</template>
	</XTable>
</template>

<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XFileInput, XDropzone } from '@lyra/ui';
import { request } from '@lyra/ui';

const flux = reactive({
	changeFile(event: Event) {
		const el = event.target as HTMLInputElement;
		const file = el?.files?.[0];
		console.log(file);
	},
	async fileUploads(event: Event) {
		const el = event.target as HTMLInputElement;
		const file = el?.files?.[0];

		const formData = new FormData();
		if (file) formData.append('userfile', file);

		await request('/file-uploads', {
			method: 'POST',
			body: formData,
		});
	},
	async importData(event: Event) {
		const el = event.target as HTMLInputElement;
		const file = el?.files?.[0];

		const formData = new FormData();
		if (file) formData.append('userfile', file);

		await request('/file-uploads/import-data', {
			method: 'POST',
			body: formData,
		});
	},
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'FileInput' }]" />

	<h1 class="my-4 text-4xl font-extrabold">FileInput</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XFileInput label="Example label" @change="flux.changeFile" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Multiple</h2>

		<XCard>
			<XFileInput label="Example label" multiple />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XFileInput label="Example label" disabled />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Upload file to Cloudinary</h2>

		<XCard>
			<XFileInput label="Example label" @change="flux.fileUploads" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Import data from Excel</h2>

		<XCard>
			<XFileInput label="Example label" @change="flux.importData" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Dropzone</h2>

		<XCard>
			<XDropzone label="Example label" />
		</XCard>
	</section>
</template>

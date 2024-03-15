<script lang="ts" setup>
import { ref, reactive } from 'vue';
import { XBreadcrumb, XCard, XTextField, XCode, XChipField } from '@lyra/ui';

const flux = reactive({
	textField1: '',
	textField2: 'Vue is excellent',
	chipField: ['Vue', 'Fastify', 'Tauri'],
});

const alpha = ref('');
const alphanum = ref('');
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'TextField' }]" />

	<h1 class="my-4 text-4xl font-extrabold">TextField</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XTextField v-model:value="flux.textField1" placeholder="Type here..." />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Label</h2>

		<XCard>
			<XTextField
				v-model:value="flux.textField1"
				label="Example label"
				placeholder="Type here..."
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Required</h2>

		<XCard>
			<XTextField
				v-model:value="flux.textField1"
				label="Example label"
				placeholder="Type here..."
				required
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Invalid</h2>

		<XCard>
			<XTextField
				v-model:value="flux.textField1"
				label="Example label"
				placeholder="Type here..."
				required
				invalid
			/>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Error Message</h3>

			<XCard>
				<XTextField
					v-model:value="flux.textField1"
					label="Example label"
					placeholder="Type here..."
					required
					invalid="Incorrect entry"
				/>
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XTextField
				v-model:value="flux.textField1"
				label="Example label"
				placeholder="Type here..."
				disabled
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Clearable</h2>

		<XCard>
			<XTextField
				v-model:value="flux.textField2"
				label="Example label"
				placeholder="Type here..."
				clearable
			/>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Append Icons</h3>

			<XCard>
				<XTextField
					v-model:value="flux.textField2"
					label="Example label"
					placeholder="Type here..."
					append="i-mdi-user-circle"
					clearable
				/>
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Icons</h2>

		<XCard>
			<div class="grid grid-cols-1 gap-4 md:grid-cols-2">
				<XTextField
					v-model:value="flux.textField1"
					label="Example label"
					placeholder="Type here..."
					prepend="i-mdi-user-circle"
				/>

				<XTextField
					v-model:value="flux.textField1"
					label="Example label"
					placeholder="Type here..."
					append="i-mdi-user-circle"
				/>

				<XTextField
					v-model:value="flux.textField1"
					label="Example label"
					placeholder="Type here..."
					prepend="i-mdi-user-circle"
					invalid="Incorrect entry"
				/>

				<XTextField
					v-model:value="flux.textField1"
					label="Example label"
					placeholder="Type here..."
					append="i-mdi-user-circle"
					invalid="Incorrect entry"
				/>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Pattern</h2>

		<XCard>
			<div class="grid grid-cols-1 gap-4 md:grid-cols-2">
				<XTextField v-model:value="alpha" label="Alphabetic" pattern="^[A-Za-z]+$">
					<XCode class="ms-1">^[A-Za-z]+$</XCode>
				</XTextField>

				<XTextField v-model:value="alphanum" label="Alphanumeric" pattern="^[A-Za-z0-9]+$">
					<XCode class="ms-1">^[A-Za-z0-9]+$</XCode>
				</XTextField>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">ChipField</h2>

		<XCard>
			<XChipField v-model:value="flux.chipField" placeholder="Type here...">
				Example label
			</XChipField>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Disabled</h3>

			<XCard>
				<XChipField v-model:value="flux.chipField" placeholder="Type here..." disabled>
					Example label
				</XChipField>
			</XCard>
		</section>
	</section>
</template>

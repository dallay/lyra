<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XCodeBlock, XSwitch } from '@lyra/ui';

import Basic from './Basic.vue';
import BasicCode from './Basic.vue?raw';

const codeImport = `import { XSwitch } from '@lyra/ui';`;

const codeReadonly = `<XSwitch checked readonly>Switch (checked)</XSwitch>
<XSwitch :checked="false" readonly>Switch (unchecked)</XSwitch>`;

const codeDisabled = `<XSwitch v-model:value="checked" disabled>Switch</XSwitch>`;

const codeWithoutLabels = `<XSwitch v-model:value="checked" />`;

const flux = reactive({
	switch1: false,
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'Switch' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Switch</h1>
	<p class="my-4">Switch is used to select a boolean value.</p>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Import</h2>

		<XCodeBlock :code="codeImport" language="ts" />
	</section>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>
		<p class="my-4">Two-way value binding is defined using `v-model:value`.</p>

		<XCard>
			<Basic />
		</XCard>

		<XCodeBlock :code="BasicCode" language="html" />
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Readonly</h2>

		<XCard>
			<div class="flex gap-4">
				<XSwitch checked readonly>Switch (checked)</XSwitch>
				<XSwitch :checked="false" readonly>Switch (unchecked)</XSwitch>
			</div>
		</XCard>

		<XCodeBlock :code="codeReadonly" language="html" />
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>
		<p class="my-4">When `disabled` is present, the element cannot be edited and focused.</p>

		<XCard>
			<XSwitch v-model:value="flux.switch1" disabled>Switch</XSwitch>
		</XCard>

		<XCodeBlock :code="codeDisabled" language="html" />
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Without labels</h2>

		<XCard>
			<XSwitch v-model:value="flux.switch1" />
		</XCard>

		<XCodeBlock :code="codeWithoutLabels" language="html" />
	</section>
</template>

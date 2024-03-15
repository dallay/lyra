<script lang="ts" setup>
import { XBreadcrumb, XPanel } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Layout' }, { text: 'Panel' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Panel</h1>

	<div class="my-8">
		<div class="mb-4 text-3xl font-bold">Basic</div>

		<XPanel>
			<template #header>
				<div>What is Vue?</div>
			</template>

			<template #content>
				<div>
					Vue (pronounced /vjuː/, like view) is a JavaScript framework for building user interfaces.
					It builds on top of standard HTML, CSS, and JavaScript and provides a declarative and
					component-based programming model that helps you efficiently develop user interfaces, be
					they simple or complex.
				</div>
			</template>
		</XPanel>
	</div>

	<div class="my-8">
		<div class="mb-4 pt-6 text-3xl font-bold">Initial Status</div>

		<XPanel :status="false">
			<template #header>
				<div>What is Vue?</div>
			</template>

			<template #content>
				<div>
					Vue (pronounced /vjuː/, like view) is a JavaScript framework for building user interfaces.
					It builds on top of standard HTML, CSS, and JavaScript and provides a declarative and
					component-based programming model that helps you efficiently develop user interfaces, be
					they simple or complex.
				</div>
			</template>
		</XPanel>
	</div>
</template>

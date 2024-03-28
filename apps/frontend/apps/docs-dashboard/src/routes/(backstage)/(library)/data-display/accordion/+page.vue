<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XAccordion } from '@lyra/ui';

const accordionGroup = ref<string[]>([]);
const accordionGroupMultiple = ref<string[]>([]);
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Accordion' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Accordion</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XAccordion>
			<XAccordion.Item>
				<template #header>
					<div>Approachable</div>
				</template>

				<template #content>
					<div>
						Builds on top of standard HTML, CSS and JavaScript with intuitive API and world-class
						documentation.
					</div>
				</template>
			</XAccordion.Item>

			<XAccordion.Item>
				<template #header>
					<div>Performant</div>
				</template>

				<template #content>
					<div>
						Truly reactive, compiler-optimized rendering system that rarely requires manual
						optimization.
					</div>
				</template>
			</XAccordion.Item>

			<XAccordion.Item>
				<template #header>
					<div>Versatile</div>
				</template>

				<template #content>
					<div>
						A rich, incrementally adoptable ecosystem that scales between a library and a
						full-featured framework.
					</div>
				</template>
			</XAccordion.Item>
		</XAccordion>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Model</h2>

		<div class="mb-2">{{ accordionGroup }}</div>

		<XAccordion v-model="accordionGroup">
			<XAccordion.Item value="approachable">
				<template #header>
					<div>Approachable</div>
				</template>

				<template #content>
					<div>
						Builds on top of standard HTML, CSS and JavaScript with intuitive API and world-class
						documentation.
					</div>
				</template>
			</XAccordion.Item>

			<XAccordion.Item value="performant">
				<template #header>
					<div>Performant</div>
				</template>

				<template #content>
					<div>
						Truly reactive, compiler-optimized rendering system that rarely requires manual
						optimization.
					</div>
				</template>
			</XAccordion.Item>

			<XAccordion.Item value="versatile">
				<template #header>
					<div>Versatile</div>
				</template>

				<template #content>
					<div>
						A rich, incrementally adoptable ecosystem that scales between a library and a
						full-featured framework.
					</div>
				</template>
			</XAccordion.Item>
		</XAccordion>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Model with multiple</h2>

		<div class="mb-2">{{ accordionGroupMultiple }}</div>

		<XAccordion v-model="accordionGroupMultiple" multiple>
			<XAccordion.Item value="approachable">
				<template #header>
					<div>Approachable</div>
				</template>

				<template #content>
					<div>
						Builds on top of standard HTML, CSS and JavaScript with intuitive API and world-class
						documentation.
					</div>
				</template>
			</XAccordion.Item>

			<XAccordion.Item value="performant">
				<template #header>
					<div>Performant</div>
				</template>

				<template #content>
					<div>
						Truly reactive, compiler-optimized rendering system that rarely requires manual
						optimization.
					</div>
				</template>
			</XAccordion.Item>

			<XAccordion.Item value="versatile">
				<template #header>
					<div>Versatile</div>
				</template>

				<template #content>
					<div>
						A rich, incrementally adoptable ecosystem that scales between a library and a
						full-featured framework.
					</div>
				</template>
			</XAccordion.Item>
		</XAccordion>
	</section>
</template>

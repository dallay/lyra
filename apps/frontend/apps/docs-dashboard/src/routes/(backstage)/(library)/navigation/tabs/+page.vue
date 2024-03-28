<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XTabs, XCode } from '@lyra/ui';

const flux = reactive({
	tabControlled: 3,
	tabControlledCustomValue: 'vue',
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Navigation' }, { text: 'Tabs' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Tabs</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XTabs>
				<XTabs.Tab title="Angular">
					<div class="p-4 text-rose-500">Angular Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="React">
					<div class="p-4 text-sky-500">React Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Svelte">
					<div class="p-4 text-amber-500">Svelte Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Vue">
					<div class="p-4 text-emerald-500">Vue Content</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Dynamic</h2>

		<XCard>
			<XTabs>
				<XTabs.Tab
					v-for="tab in [
						{ title: 'Tab 1', content: 'Tab 1 Content' },
						{ title: 'Tab 2', content: 'Tab 2 Content' },
						{ title: 'Tab 3', content: 'Tab 3 Content' },
					]"
					:key="tab.title"
					:title="tab.title"
				>
					<div class="p-4 text-emerald-500">{{ tab.content }}</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Controlled</h2>

		<div class="my-2 flex items-center gap-2">
			<div>Active Index:</div>
			<XCode>{{ flux.tabControlled }}</XCode>
		</div>

		<XCard>
			<XTabs v-model="flux.tabControlled">
				<XTabs.Tab title="Angular" value="angular">
					<div class="p-4 text-red-500">Angular Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="React" value="react">
					<div class="p-4 text-blue-500">React Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Svelte" value="svelte">
					<div class="p-4 text-orange-500">Svelte Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Vue" value="vue">
					<div class="p-4 text-green-500">Vue Content</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Custom Value</h3>

			<div class="my-2 flex items-center gap-2">
				<div>Active Value:</div>
				<XCode>{{ flux.tabControlledCustomValue }}</XCode>
			</div>

			<XCard>
				<XTabs v-model="flux.tabControlledCustomValue">
					<XTabs.Tab title="Angular" value="angular">
						<div class="p-4 text-red-500">Angular Content</div>
					</XTabs.Tab>

					<XTabs.Tab title="React" value="react">
						<div class="p-4 text-blue-500">React Content</div>
					</XTabs.Tab>

					<XTabs.Tab title="Svelte" value="svelte">
						<div class="p-4 text-orange-500">Svelte Content</div>
					</XTabs.Tab>

					<XTabs.Tab title="Vue" value="vue">
						<div class="p-4 text-green-500">Vue Content</div>
					</XTabs.Tab>
				</XTabs>
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Scrollable</h2>

		<XCard>
			<XTabs>
				<XTabs.Tab v-for="num in 20" :key="num" :title="`Tab ${num}`">
					<div class="p-4 text-red-500">Content {{ num }}</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="mb-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XTabs>
				<XTabs.Tab title="Angular">
					<div class="p-4 text-red-500">Angular Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="React" :disabled="true">
					<div class="p-4 text-blue-500">React Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Svelte">
					<div class="p-4 text-orange-500">Svelte Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Vue">
					<div class="p-4 text-green-500">Vue Content</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="mb-4 pt-6 text-3xl font-bold">Closeable</h2>

		<XCard>
			<XTabs closeable>
				<XTabs.Tab title="Angular">
					<div class="p-4 text-red-500">Angular Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="React">
					<div class="p-4 text-blue-500">React Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Svelte">
					<div class="p-4 text-orange-500">Svelte Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Vue">
					<div class="p-4 text-green-500">Vue Content</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="mb-4 pt-6 text-3xl font-bold">Template</h2>

		<XCard>
			<XTabs>
				<XTabs.Tab>
					<template #title>
						<div class="i-simple-icons-angular mr-1 h-5 w-5"></div>
						<div>Angular</div>
					</template>

					<div class="p-4 text-red-500">Angular Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="React">
					<template #title>
						<div class="i-simple-icons-react mr-1 h-5 w-5"></div>
						<div>React</div>
					</template>

					<div class="p-4 text-blue-500">React Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Svelte">
					<template #title>
						<div class="i-simple-icons-svelte mr-1 h-5 w-5"></div>
						<div>Svelte</div>
					</template>

					<div class="p-4 text-orange-500">Svelte Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Vue">
					<template #title>
						<div class="i-simple-icons-vuedotjs mr-1 h-5 w-5"></div>
						<div>Vue</div>
					</template>

					<div class="p-4 text-green-500">Vue Content</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="mb-4 pt-6 text-3xl font-bold">Router</h2>

		<XCard>
			<XTabs>
				<XTabs.Tab title="Angular" to="/navigation/tabs">
					<div class="p-4 text-red-500">Angular Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="React" to="/navigation/tabs">
					<div class="p-4 text-blue-500">React Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Svelte" to="/navigation/tabs">
					<div class="p-4 text-orange-500">Svelte Content</div>
				</XTabs.Tab>

				<XTabs.Tab title="Vue" to="/navigation/tabs">
					<div class="p-4 text-green-500">Vue Content</div>
				</XTabs.Tab>
			</XTabs>
		</XCard>
	</section>
</template>

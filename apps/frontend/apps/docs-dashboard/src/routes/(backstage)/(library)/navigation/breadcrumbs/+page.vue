<script lang="ts" setup>
import { XBreadcrumb, XCard } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Navigation' }, { text: 'Breadcrumbs' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Breadcrumb</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XBreadcrumb
				:items="[
					{ text: 'Home', to: '/navigation/breadcrumbs' },
					{ text: 'Projects', href: '#breadcrumb' },
					{ text: 'Flowbite' },
				]"
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Icon</h2>

		<XCard>
			<XBreadcrumb
				:items="[
					{ icon: 'i-material-symbols-house-rounded', text: 'Home', to: '/navigation/breadcrumbs' },
					{ icon: 'i-material-symbols-folder-rounded', text: 'Projects', href: '#breadcrumb' },
					{ icon: 'i-material-symbols-local-fire-department-rounded', text: 'Flowbite' },
				]"
			/>
		</XCard>
	</section>
</template>

<script lang="ts" setup>
import { XBreadcrumb, XCard, XChip } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Chip' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Chip</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="flex flex-wrap items-center justify-center gap-4">
				<XChip>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (primary)</div>
				</XChip>

				<XChip color="secondary">
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (secondary)</div>
				</XChip>

				<XChip color="success">
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (success)</div>
				</XChip>

				<XChip color="warning">
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (warning)</div>
				</XChip>

				<XChip color="danger">
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (danger)</div>
				</XChip>

				<XChip color="info">
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (info)</div>
				</XChip>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Closable</h2>

		<XCard>
			<div class="flex flex-wrap items-center justify-center gap-4">
				<XChip closable>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (primary)</div>
				</XChip>

				<XChip color="secondary" closable>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (secondary)</div>
				</XChip>

				<XChip color="success" closable>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (success)</div>
				</XChip>

				<XChip color="warning" closable>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (warning)</div>
				</XChip>

				<XChip color="danger" closable>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (danger)</div>
				</XChip>

				<XChip color="info" closable>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (info)</div>
				</XChip>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<div class="flex flex-wrap items-center justify-center gap-4">
				<XChip disabled>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (primary)</div>
				</XChip>

				<XChip color="secondary" disabled>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (secondary)</div>
				</XChip>

				<XChip color="success" disabled>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (success)</div>
				</XChip>

				<XChip color="warning" disabled>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (warning)</div>
				</XChip>

				<XChip color="danger" disabled>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (danger)</div>
				</XChip>

				<XChip color="info" disabled>
					<div class="i-fa-envelope h-4 w-4"></div>
					<div class="ml-1">Mail (info)</div>
				</XChip>
			</div>
		</XCard>
	</section>
</template>

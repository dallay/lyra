<script lang="ts" setup>
import { XBreadcrumb, XCard, XBadge } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Badge' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Badge</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="gap-15 flex items-center justify-center">
				<XBadge>
					<div class="i-fa-envelope h-6 w-6"></div>
				</XBadge>

				<XBadge count="7">
					<div class="i-fa-envelope h-6 w-6"></div>
				</XBadge>

				<XBadge count="77">
					<div class="i-fa-envelope h-6 w-6"></div>
				</XBadge>

				<XBadge count="99+">
					<div class="i-fa-envelope h-6 w-6"></div>
				</XBadge>
			</div>

			<div class="gap-15 mt-6 flex items-center justify-center">
				<XBadge>
					<div>Link something</div>
				</XBadge>
			</div>
		</XCard>
	</section>
</template>

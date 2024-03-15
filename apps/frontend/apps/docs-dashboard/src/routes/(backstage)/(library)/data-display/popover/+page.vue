<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XPopover, XCode, XButton } from '@lyra/ui';

const controlled = ref(false);
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Popover' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Popover</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XPopover>
				<XButton icon="i-mdi-atom" />

				<template #content>
					<div class="px-4 py-2">The content of the Popover.</div>
				</template>
			</XPopover>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="mb-4 pt-6 text-3xl font-bold">Controlled</h2>

		<XCard>
			<div class="flex items-center gap-2">
				Status:
				<XCode>{{ controlled }}</XCode>
				<XButton icon="i-mdi-add" @click="controlled = true" />
				<XButton icon="i-mdi-minus" @click="controlled = false" />
			</div>

			<XPopover v-model="controlled">
				<XButton icon="i-mdi-atom" @click="controlled = !controlled" />

				<template #content>
					<div class="flex gap-2 px-4 py-2">
						Status:
						<XCode>{{ controlled }}</XCode>
					</div>
				</template>
			</XPopover>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XPopover disabled>
				<XButton icon="i-mdi-atom" disabled />

				<template #content>
					<div class="px-4 py-2">The content of the Popover.</div>
				</template>
			</XPopover>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Placement</h2>

		<XCard>
			<div class="h-50 flex justify-between">
				<div>
					<XPopover>
						<XButton icon="i-mdi-atom" />

						<template #content>
							<div class="px-4 py-2">The content of the Popover.</div>
						</template>
					</XPopover>
				</div>

				<div>
					<XPopover>
						<XButton icon="i-mdi-atom" />

						<template #content>
							<div class="px-4 py-2">The content of the Popover.</div>
						</template>
					</XPopover>
				</div>
			</div>

			<div class="h-50 flex justify-evenly">
				<div v-for="num in 6" :key="num">
					<XPopover>
						<XButton icon="i-mdi-atom" />

						<template #content>
							<div class="px-4 py-2">The content of the Popover.</div>
						</template>
					</XPopover>
				</div>
			</div>

			<div class="h-50 flex justify-between">
				<div>
					<XPopover>
						<XButton icon="i-mdi-atom" />

						<template #content>
							<div class="px-4 py-2">The content of the Popover.</div>
						</template>
					</XPopover>
				</div>

				<div>
					<XPopover>
						<XButton icon="i-mdi-atom" />

						<template #content>
							<div class="px-4 py-2">The content of the Popover.</div>
						</template>
					</XPopover>
				</div>
			</div>
		</XCard>
	</section>
</template>

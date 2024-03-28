<script lang="ts" setup>
import { XSkeleton } from '@lyra/ui';

import useStore from './store';
import NavLink from './NavLink.vue';

const { state } = useStore();
</script>

<template>
	<div v-if="state.userLoading" class="flex h-full flex-col">
		<XSkeleton class="h-10 w-full" />

		<div class="mt-4 h-9 w-full py-2">
			<XSkeleton class="h-full w-1/3" />
		</div>

		<XSkeleton v-for="num in 7" :key="num" class="mb-1 h-10 w-full" />

		<div class="mt-4 h-9 w-full py-2">
			<XSkeleton class="h-full w-1/3" />
		</div>

		<XSkeleton v-for="num in 4" :key="num" class="mb-1 h-10 w-full" />
	</div>

	<template v-else>
		<template v-for="link in state.listOfLinks" :key="link.name">
			<NavLink
				:icon="link.icon"
				:name="link.name"
				:to="link.to"
				:permissions="link.permissions"
				:sub="link.sub"
				:level="link.level"
				:status="link.status"
			/>
		</template>
	</template>
</template>

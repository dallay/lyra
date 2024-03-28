<script lang="ts" setup>
import { XBreadcrumb } from '@lyra/ui';

import Basic from './Basic.vue';
import Selectable from './Selectable.vue';
import NoData from './NoData.vue';
</script>

<template>
	<XBreadcrumb
		:items="[
			{ text: 'Library' },
			{ text: 'Data Display' },
			{ text: 'Table' },
			{ text: 'Static Table' },
		]"
	/>

	<div class="my-4 text-4xl font-extrabold">Static Table</div>

	<Basic class="my-8" />
	<Selectable class="my-8" />
	<NoData class="my-8" />
</template>

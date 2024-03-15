<script lang="ts" setup>
import { XBreadcrumb } from '@lyra/ui';

import Basic from './Basic.vue';
import Touched from './Touched.vue';
</script>

<template>
	<XBreadcrumb
		:items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'Form Validation' }]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">Form Validation</h1>

	<Basic />
	<Touched />
</template>

<script setup lang="ts">
import { XBreadcrumb, XTable } from '@lyra/ui';
import { reactive } from 'vue';

const flux = reactive({
  columns: [
    { key: 'name', name: 'Name', sortable: true },
  ],});
</script>

<template>
  <XBreadcrumb
    :items="[
			{ text: 'Settings', icon: 'i-material-symbols:settings' },
			{ text: 'Publication', icon: 'i-ooui:articles-rtl' },
			{ text: 'Subscriber Form', icon: 'i-material-symbols-light:dynamic-form-rounded'},
		]"
    class="mb-2.5"
  />
  <h2 class="my-4 text-3xl font-bold">Subscriber Forms</h2>
  <p>Create customizable and embeddable email subscribe forms for your other websites.</p>
  <XTable
    v-model:control="flux.control"
    stickyHeader
    :columns="flux.columns"
    :rows="state.subscribers"
    @change="change"
  ><XTable>
</template>

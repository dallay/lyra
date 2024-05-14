<script lang="ts" setup>
import { XCodeBlock } from '@lyra/ui';
import { computed } from 'vue';

const props = defineProps<{
	title: string;
	description: string;
	iframeSrc: string;
	iframeWidth: string;
	iframeHeight: string;
	iframeStyle: string;
}>();

const _iframeCode = computed(() => {
	return `<iframe src="${props.iframeSrc}" data-test-id="lyra-embed" width="${props.iframeWidth}" height="${props.iframeHeight}" border="0" style="${props.iframeStyle}"></iframe>`;
});
</script>

<template>
  <div>
    <div class="relative inline-flex w-full items-center justify-center">
      <hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
      <span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800">
				{{ title }}
			</span>
    </div>
    <p class="text-wrap">
      {{ description }}
    </p>
    <XCodeBlock :code="iframeCode" language="html" />
    <div class="border border-gray-200 dark:border-gray-700 my-6">
      <iframe
        :height="iframeHeight"
        :src="iframeSrc"
        :style="iframeStyle"
        :width="iframeWidth"
        border="0"
        data-test-id="lyra-embed"
      ></iframe>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>

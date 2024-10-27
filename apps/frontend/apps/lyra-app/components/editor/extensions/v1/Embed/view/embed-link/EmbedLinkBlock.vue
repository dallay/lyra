<template>
  <NodeViewWrapper>
    <Card class="relative h-auto flex bg-clip-border rounded-xl bg-white text-gray-700 shadow-md w-full dark:bg-gray-800 dark:text-gray-300">
      <div v-if="props.node.attrs.layout === 'image-left'" class="flex flex-row">
        <div class="relative w-2/5 m-0 overflow-hidden text-gray-700 bg-white rounded-r-none bg-clip-border rounded-xl shrink-0 dark:bg-gray-700">
          <NuxtImg :src="props.node.attrs.image" :alt="props.node.attrs.title" class="object-cover w-full h-full" />
        </div>
        <div class="p-6">
          <h5 class="line-clamp-1 block mb-2 font-sans text-xl antialiased font-semibold leading-snug tracking-normal text-blue-gray-900 dark:text-white">
            {{ props.node.attrs.title }}
          </h5>
          <p class="line-clamp-2 block mb-8 font-sans text-base antialiased font-normal leading-relaxed text-gray-700 dark:text-gray-400">
            {{ props.node.attrs.description }}
          </p>
          <a :href="url" target="_blank" rel="noopener noreferrer" class="line-clamp-1 block text-xs font-sans hover:underline overflow-hidden dark:text-blue-400">
            {{ url }}
          </a>
        </div>
      </div>
      <div v-else-if="props.node.attrs.layout === 'image-right'" class="flex flex-row-reverse">
        <div class="relative w-2/5 m-0 overflow-hidden text-gray-700 bg-white rounded-l-none bg-clip-border rounded-xl shrink-0 dark:bg-gray-700">
          <NuxtImg :src="props.node.attrs.image" :alt="props.node.attrs.title" class="object-cover w-full h-full" />
        </div>
        <div class="p-6">
          <h5 class="line-clamp-1 block mb-2 font-sans text-xl antialiased font-semibold leading-snug tracking-normal text-blue-gray-900 dark:text-white">
            {{ props.node.attrs.title }}
          </h5>
          <p class="line-clamp-2 block mb-8 font-sans text-base antialiased font-normal leading-relaxed text-gray-700 dark:text-gray-400">
            {{ props.node.attrs.description }}
          </p>
          <a :href="url" target="_blank" rel="noopener noreferrer" class="line-clamp-1 block text-xs font-sans hover:underline overflow-hidden dark:text-blue-400">
            {{ url }}
          </a>
        </div>
      </div>
      <div v-else class="flex flex-col">
        <div class="relative w-full m-0 overflow-hidden text-gray-700 bg-white rounded-b-none bg-clip-border rounded-xl shrink-0 dark:bg-gray-700">
          <NuxtImg :src="props.node.attrs.image" :alt="props.node.attrs.title" class="object-cover w-full h-full" />
        </div>
        <div class="p-6">
          <h5 class="line-clamp-1 block mb-2 font-sans text-xl antialiased font-semibold leading-snug tracking-normal text-blue-gray-900 dark:text-white">
            {{ props.node.attrs.title }}
          </h5>
          <p class="line-clamp-2 block mb-8 font-sans text-base antialiased font-normal leading-relaxed text-gray-700 dark:text-gray-400">
            {{ props.node.attrs.description }}
          </p>
          <a :href="url" target="_blank" rel="noopener noreferrer" class="line-clamp-1 block text-xs font-sans hover:underline overflow-hidden dark:text-blue-400">
            {{ url }}
          </a>
        </div>
      </div>
    </Card>
  </NodeViewWrapper>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { nodeViewProps, NodeViewWrapper } from "@tiptap/vue-3";
import { Card } from "~/components/ui/card";

const props = defineProps(nodeViewProps);
const url = ref<string>(props.node.attrs.url);
</script>

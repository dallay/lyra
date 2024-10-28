<template>
  <node-view-wrapper>
    <EmbedInput :editor="props.editor" :get-pos="props.getPos" :embedType="props.node.attrs.embedType" />
    <div v-if="loading" class="flex items-center justify-center w-full h-32">
      <LoaderCircle class="size-6 text-muted-foreground animate-spin dark:text-gray-400" />
    </div>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { Editor, NodeViewWrapper, nodeViewProps } from "@tiptap/vue-3";
import { LoaderCircle } from "lucide-vue-next";
import EmbedInput from './view/EmbedInput.vue';

const props = defineProps(nodeViewProps);
const loading = ref<boolean>(false);
</script>

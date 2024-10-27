<template>
  <node-view-wrapper>
    <EmbedInput :editor="editor" :get-pos="getPos"/>
    <div v-if="loading" class="flex items-center justify-center w-full h-32">
      <LoaderCircle class="size-6 text-muted-foreground animate-spin dark:text-gray-400" />
    </div>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { Editor, NodeViewWrapper } from "@tiptap/vue-3";
import { LoaderCircle } from "lucide-vue-next";
import EmbedInput from './view/EmbedInput.vue';

interface EmbedProps {
  getPos: () => number;
  editor: Editor
}

const props = defineProps<EmbedProps>();
const loading = ref<boolean>(false);
</script>

<template>
  <node-view-wrapper class="w-full mx-auto p-6">
    <div class="border-2 border-dashed rounded-lg p-8 text-center transition-colors">
      <Input
        v-model="url"
        placeholder="Enter YouTube URL"
        class="border p-2 rounded w-full"
      />
      <div class="mt-4 flex justify-center space-x-2">
        <Button @click.prevent="embedVideo" variant="outline">
          Embed Video
        </Button>
      </div>
    </div>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';

// Props destructuring
const props = defineProps(nodeViewProps);
const { editor } = props;

const url = ref<string>('');

const embedVideo = () => {
  if (url.value) {
    editor.chain().focus().setYoutubeVideo({ src: url.value }).run();
  }
};
</script>

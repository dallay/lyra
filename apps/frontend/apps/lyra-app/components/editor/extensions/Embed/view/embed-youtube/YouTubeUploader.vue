<template>
  <node-view-wrapper class="relative w-full items-center">
    <Input
      v-model="url"
      :id="youtubeId"
      type="text"
      placeholder="Paste or type a YouTube URL"
      class="pl-10"
      @input="embedVideo"
    />
    <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
      <Suspense>
        <Icon name="si:youtube-fill" class="size-6 text-muted-foreground" />
        <template #fallback>
          <LoaderCircle class="size-6 text-muted-foreground animate-spin" />
        </template>
      </Suspense>
    </span>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
import { Input } from '@/components/ui/input';
import { LoaderCircle } from 'lucide-vue-next';

const props = defineProps(nodeViewProps);
const youtubeId = ref<string>(`youtube-${crypto.randomUUID()}`);
const { editor } = props;
const url = ref<string>('');

const embedVideo = () => {
  if (url.value) {
    editor.chain().focus().setYoutubeVideo({ src: url.value }).run();
  }
};

watch(url, (newUrl) => {
  embedVideo();
});
</script>

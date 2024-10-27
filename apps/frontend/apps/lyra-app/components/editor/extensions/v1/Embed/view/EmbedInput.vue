<template>
  <div v-if="!metadata"  class="relative w-full items-center">
    <Input ref="inputRef" v-model="url" :id="embedInputId" type="text" placeholder="Paste or type a URL"
      class="pl-10 dark:bg-gray-800 dark:text-white" @input="debouncedEmbedLink" />
    <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
      <Suspense>
        <Icon name="lucide:link-2" class="size-6 text-muted-foreground dark:text-gray-400" />
        <template #fallback>
          <LoaderCircle class="size-6 text-muted-foreground animate-spin dark:text-gray-400" />
        </template>
      </Suspense>
    </span>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import { Input } from "@/components/ui/input";
import { LoaderCircle } from "lucide-vue-next";
import { debounce } from "@lyra/utilities";
import type { EmbedMetadata } from "../types";
import type { Editor } from "@tiptap/vue-3";
const { $api } = useNuxtApp();

interface EmbedInputProps {
  getPos: () => number;
  editor: Editor
}

const url = ref<string>("");
const embedInputId = ref<string>(`embed-input-${crypto.randomUUID()}`);
const inputRef = ref<HTMLInputElement | null>(null);
const loading = ref<boolean>(false);
const props = defineProps<EmbedInputProps>();
const metadata = ref<EmbedMetadata | null>(null);

const fetchMetadata = async (url: string) => {
  loading.value = true;
  try {
    const response = await $api.linkPreview.fetchLinkPreview(url);
    metadata.value = {
      url,
      image: response.imageUrl || '',
      title: response.title,
      description: response.description,
    };
  } catch (error) {
    console.error("Error fetching metadata:", error);
    metadata.value = null;
  } finally {
    loading.value = false;
  }
};

const embedLink = async () => {
  if (url.value) {
    if (url.value.includes("youtube.com") || url.value.includes("youtu.be")) {
      // editor.chain().focus().setYoutubeVideo({ src: url.value }).run();
      alert("Youtube videos are not supported yet.");
    } else {
      await fetchMetadata(url.value);
      if(metadata.value)
      props.editor
      .chain()
      .setEmbedLinkBlock({...metadata.value, layout: 'right'})
      .deleteRange({ from: props.getPos(), to: props.getPos() })
      .focus()
      .run()
    }
  }
};

const debouncedEmbedLink = debounce(embedLink, 300);

watch(url, (newUrl) => {
  debouncedEmbedLink();
});
</script>

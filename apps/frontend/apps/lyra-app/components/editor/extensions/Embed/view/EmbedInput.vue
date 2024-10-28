<template>
  <div v-if="isEmbedInputVisible" class="relative w-full items-center">
    <Input ref="inputRef" v-model="url" :id="embedInputId" type="text" :placeholder="placeholder"
      class="pl-10 dark:bg-gray-800 dark:text-white" @input="generateEmbedDebouncer" autocomplete="on" />
    <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
      <Suspense>
        <Icon :name="inputIcon" class="size-6 text-muted-foreground dark:text-gray-400" />
        <template #fallback>
          <LoaderCircle class="size-6 text-muted-foreground animate-spin dark:text-gray-400" />
        </template>
      </Suspense>
    </span>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, computed } from "vue";
import { Input } from "@/components/ui/input";
import { LoaderCircle } from "lucide-vue-next";
import { debounce } from "@lyra/utilities";
import type { EmbedMetadata, SupportedEmbeds } from "../types";
import type { Editor } from "@tiptap/core";
const { $api } = useNuxtApp();

interface EmbedInputProps {
  getPos: () => number;
  editor: Editor;
  embedType?: SupportedEmbeds;
}

const url = ref<string>("");
const embedInputId = ref<string>(`embed-input-${crypto.randomUUID()}`);
const inputRef = ref<HTMLInputElement | null>(null);
const loading = ref<boolean>(false);
const props = defineProps<EmbedInputProps>();
const metadata = ref<EmbedMetadata | null>(null);
const inputIcon = computed(() => {
  switch (props.embedType) {
    case "youtube":
      return "lucide:youtube";
    case "link":
      return "lucide:link-2";
    default:
      return "lucide:link-2";
  }
});

const placeholder = computed(() => {
  switch (props.embedType) {
    case "youtube":
      return "Paste a YouTube video URL";
    case "link":
      return "Paste or type a URL";
    default:
      return "Paste or type a URL";
  }
});

const done = ref<boolean>(false);
const isEmbedInputVisible = computed(() => {
  return !metadata.value && !done.value;
});

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

const generateEmbed = async () => {
  if (url.value) {
    if (url.value.includes("youtube.com") || url.value.includes("youtu.be")) {
      props.editor.chain().focus().setYoutubeVideo({ src: url.value }).run();
      done.value = true;
    } else {
      await fetchMetadata(url.value);
      if (metadata.value) {
        props.editor
          .chain()
          .setEmbedLinkBlock({ ...metadata.value, layout: 'right' })
          .deleteRange({ from: props.getPos(), to: props.getPos() })
          .focus()
          .run();
        done.value = true;
      }
    }
  }
};

const generateEmbedDebouncer = debounce(generateEmbed, 300);

watch(url, () => {
  generateEmbedDebouncer();
});
</script>

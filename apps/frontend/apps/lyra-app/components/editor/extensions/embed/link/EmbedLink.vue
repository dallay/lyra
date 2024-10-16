<template>
  <node-view-wrapper>
    <div v-if="!metadata" class="relative w-full items-center">
      <Input
        ref="inputRef"
        v-model="url"
        id="link"
        type="text"
        placeholder="Paste or type a URL"
        class="pl-10 dark:bg-gray-800 dark:text-white"
        @input="debouncedEmbedLink"
      />
      <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
        <Suspense>
          <Icon name="lucide:link-2" class="size-6 text-muted-foreground dark:text-gray-400" />
          <template #fallback>
            <LoaderCircle class="size-6 text-muted-foreground animate-spin dark:text-gray-400" />
          </template>
        </Suspense>
      </span>
    </div>

    <div v-if="loading" class="flex items-center justify-center w-full h-32">
      <LoaderCircle class="size-6 text-muted-foreground animate-spin dark:text-gray-400" />
    </div>

    <a
      v-if="metadata"
      :href="url"
      target="_blank"
      rel="noopener noreferrer"
      class="relative flex bg-clip-border rounded-xl bg-white text-gray-700 shadow-md w-full h-full flex-row dark:bg-gray-800 dark:text-gray-300"
    >
      <div class="relative w-2/5 h-full m-0 overflow-hidden text-gray-700 bg-white rounded-r-none bg-clip-border rounded-xl shrink-0 dark:bg-gray-700">
        <NuxtImg
          :src="metadata.image"
          :alt="metadata.title"
          class="object-cover w-full h-full"
        />
      </div>
      <div class="p-6">
        <h5 class="block mb-2 font-sans text-2xl antialiased font-semibold leading-snug tracking-normal text-blue-gray-900 dark:text-white">
          {{ metadata.title }}
        </h5>
        <p class="block mb-8 font-sans text-base antialiased font-normal leading-relaxed text-gray-700 dark:text-gray-400">
          {{ metadata.description }}
        </p>
        <a :href="url" class="block text-xs font-sans hover:underline overflow-hidden dark:text-blue-400">
          {{ url }}
        </a>
      </div>
    </a>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from "vue";
import { nodeViewProps, NodeViewWrapper } from "@tiptap/vue-3";
import { Input } from "@/components/ui/input";
import { LoaderCircle } from "lucide-vue-next";
import { debounce } from "@lyra/utilities";

// Props destructuring
const props = defineProps(nodeViewProps);
const { editor } = props;
const url = ref<string>("");
const inputRef = ref<HTMLInputElement | null>(null);
const metadata = ref<{
  image: string;
  title: string;
  description: string;
} | null>(null);
const loading = ref<boolean>(false);

// Fetch metadata for the URL
const fetchMetadata = async (url: string) => {
  loading.value = true;
  try {
    const response = await fetch(
      `https://api.linkpreview.net/?key=eec452f48a5ba95dcabdf3a853ee7048&q=${url}`
    );
    const data = await response.json();
    metadata.value = {
      image: data.image,
      title: data.title,
      description: data.description,
    };
  } catch (error) {
    console.error("Error fetching metadata:", error);
    metadata.value = null;
  } finally {
    loading.value = false;
  }
};

// Embed link on input change
const embedLink = () => {
  if (url.value) {
    if (url.value.includes("youtube.com") || url.value.includes("youtu.be")) {
      editor.chain().focus().setYoutubeVideo({ src: url.value }).run();
    } else {
      fetchMetadata(url.value);
    }
  }
};

// Debounce the embedLink function
const debouncedEmbedLink = debounce(embedLink, 300);

// Watch for changes in the URL input
watch(url, (newUrl) => {
  debouncedEmbedLink();
});
</script>

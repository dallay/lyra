<template>
  <node-view-wrapper class="w-full mx-auto p-6">
    <div
      class="border-2 border-dashed rounded-lg p-8 text-center transition-colors"
      :class="isDragActive ? 'border-primary bg-primary/10' : 'border-border hover:border-primary/50'"
      @dragover.prevent="onDragOver"
      @dragleave.prevent="onDragLeave"
      @drop.prevent="onDrop"
    >
      <input type="file" @change="onFileChange" class="hidden" ref="fileInput" />
      <div v-if="attrs.src || selectedImage">
        <img
          :src="attrs.src || selectedImage"
          alt="Uploaded"
          class="w-full h-48 object-cover rounded-md"
        />
      </div>
      <div v-else>
        <ImageIcon class="mx-auto h-12 w-12 text-gray-400" aria-hidden="true" />
        <p class="mt-2 text-sm text-gray-500">Drag and drop or</p>
      </div>
      <div class="mt-4 flex justify-center space-x-2">
        <Button @click.prevent="openFileSelector" variant="outline">
          <CloudUpload class="h-5 w-5 mr-2" />
          Upload an image
        </Button>
        <Dialog v-model:open="isModalOpen">
          <DialogTrigger asChild>
            <Button variant="outline">
              <Images class="h-5 w-5 mr-2" />
            Use from library
            </Button>
          </DialogTrigger>
          <DialogContent class="sm:max-w-[425px]">
            <div class="grid grid-cols-2 gap-4">
              <img
                v-for="(src, index) in mockLibraryImages"
                :key="index"
                :src="src"
                :alt="`Library image ${index + 1}`"
                class="w-full h-auto cursor-pointer rounded-md hover:opacity-80 transition-opacity"
                @click="selectImageFromLibrary(src)"
              />
            </div>
          </DialogContent>
        </Dialog>
      </div>
    </div>
  </node-view-wrapper>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogTrigger } from '@/components/ui/dialog';
import { Image as ImageIcon, CloudUpload, Images } from 'lucide-vue-next';

// Props destructuring
const props = defineProps(nodeViewProps);
const { node, updateAttributes, editor } = props;
const attrs = node.attrs;

const selectedImage = ref<string | null>(null);
const isModalOpen = ref(false);
const isDragActive = ref(false);
const fileInput = ref<HTMLInputElement | null>(null);

const onDragOver = () => {
  isDragActive.value = true;
};

const onDragLeave = () => {
  isDragActive.value = false;
};

const onDrop = (event: DragEvent) => {
  isDragActive.value = false;
  const file = event.dataTransfer?.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      const src = e.target?.result as string;
      selectedImage.value = src;
      updateAttributes({ src });

      // Replace the node with the original setImage command
      editor.chain().focus().setImage({ src }).run();
    };
    reader.readAsDataURL(file);
  }
};

const mockLibraryImages = [
  '/placeholder.svg?height=100&width=100',
  '/placeholder.svg?height=100&width=100',
  '/placeholder.svg?height=100&width=100',
  '/placeholder.svg?height=100&width=100',
];

const openFileSelector = () => {
  fileInput.value?.click();
};

const selectImageFromLibrary = (src: string) => {
  selectedImage.value = src;
  updateAttributes({ src });
  isModalOpen.value = false;

  // Replace the node with the original setImage command
  editor.chain().focus().setImage({ src }).run();
};

const onFileChange = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      const src = e.target?.result as string;
      selectedImage.value = src;
      updateAttributes({ src });

      // Replace the node with the original setImage command
      editor.chain().focus().setImage({ src }).run();
    };
    reader.readAsDataURL(file);
  }
};
</script>

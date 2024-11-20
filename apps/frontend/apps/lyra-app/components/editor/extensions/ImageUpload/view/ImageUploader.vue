<template>
  <div v-if="loading" class="flex items-center justify-center p-8 rounded-lg min-h-[10rem] bg-opacity-80">
    <Spinner class="text-neutral-500" size="1.5" />
  </div>
  <div
    v-else
    :class="wrapperClass"
    @drop="onDrop"
    @dragover.prevent="onDragEnter"
    @dragleave="onDragLeave"
    contenteditable="false"
  >
    <Icon name="Image" class="w-12 h-12 mb-4 text-black dark:text-white opacity-20" />
    <div class="flex flex-col items-center justify-center gap-2">
      <div class="text-sm font-medium text-center text-neutral-400 dark:text-neutral-500">
        {{ draggedInside ? 'Drop image here' : 'Drag and drop or' }}
      </div>
      <div class="mt-4 flex justify-center space-x-2">
        <Button :disabled="draggedInside" @click="handleUploadClick" variant="outline" buttonSize="small">
          <Icon name="Upload" class="mr-1" />
          Upload an image
        </Button>
        <Dialog v-model:open="isModalOpen">
          <DialogTrigger asChild>
            <Button variant="outline">
              <Icon name="Images" class="mr-1"/>
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
    <input
      class="w-0 h-0 overflow-hidden opacity-0"
      ref="fileInputRef"
      type="file"
      accept=".jpg,.jpeg,.png,.webp,.gif"
      @change="onFileChange"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { Spinner } from '@/components/ui/spinner'
import { Button } from '@/components/ui/button'
import { Icon } from '@/components/ui/icon'
import { Dialog, DialogContent, DialogTrigger } from '@/components/ui/dialog';
import { useDropZone, useFileUpload, useUploader } from './utils'

const props = defineProps<{ onUpload: (url: string) => void }>()
const isModalOpen = ref(false);

const { loading, uploadFile } = useUploader(props.onUpload)
const { handleUploadClick, fileInputRef } = useFileUpload()
const { draggedInside, onDrop, onDragEnter, onDragLeave } = useDropZone(uploadFile)

const onFileChange = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.files) {
    uploadFile(input.files[0])
  }
}

const wrapperClass = computed(() =>
  ['flex', 'flex-col', 'items-center', 'justify-center', 'px-8', 'py-10', 'rounded-lg', 'bg-opacity-80', draggedInside.value ? 'bg-neutral-100' : '']
)

const mockLibraryImages = [
  '/placeholder.svg?height=100&width=100',
  'https://picsum.photos/100',
  'https://picsum.photos/200',
  'https://picsum.photos/300',
];

const selectImageFromLibrary = (src: string) => {
  props.onUpload(src)
  isModalOpen.value = false
};
</script>

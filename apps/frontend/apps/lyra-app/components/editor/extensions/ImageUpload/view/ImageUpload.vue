<script setup lang="ts">
import { NodeViewWrapper, Editor } from '@tiptap/vue-3'
import ImageUploader from './ImageUploader.vue'

interface ImageUploadProps {
  getPos: () => number
  editor: Editor
}

const props = defineProps<ImageUploadProps>()

const onUpload = (url: string) => {
  if (url) {
    props.editor
      .chain()
      .setImageBlock({ src: url })
      .deleteRange({ from: props.getPos(), to: props.getPos() })
      .focus()
      .run()
  }
}
</script>

<template>
  <NodeViewWrapper>
    <div class="NodeImageUpload p-0 m-0" data-drag-handle>
      <ImageUploader :onUpload="onUpload" />
    </div>
  </NodeViewWrapper>
</template>

<style lang="scss" scoped>
.NodeImageUpload {
  @apply rounded border-2 border-dotted border-black border-opacity-10 p-2 dark:border-neutral-500;
    transition: border 160ms cubic-bezier(0.45, 0.05, 0.55, 0.95);

    &:hover {
      @apply border-opacity-30;
    }

    &:has(.is-active),
    &.has-focus {
      @apply border-opacity-40;
    }
}
</style>

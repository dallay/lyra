<template>
  <div
    class="relative prose prose-zinc dark:prose-invert focus:outline-none flex flex-col flex-1 h-full"
    ref="menuContainerRef"
  >
    <EditorContent :editor="editor" class="relative flex-1" />
    <div v-if="editor">
      <TableColumnMenu :editor="editor" :appendTo="menuContainerRef || undefined" />
      <TableRowMenu :editor="editor" :appendTo="menuContainerRef || undefined" />
      <ImageBlockMenu :editor="editor" :appendTo="menuContainerRef || undefined" />
      <LinkMenu :editor="editor" :appendTo="menuContainerRef || undefined" />
      <TextMenu :editor="editor" :appendTo="menuContainerRef || undefined" />
      <ContentItemMenu :editor="editor" :appendTo="menuContainerRef || undefined" />
    </div>
  </div>
</template>

<script setup lang="ts">
import {useTemplateRef, onBeforeUnmount, onMounted, defineProps} from 'vue';
import { EditorContent } from '@tiptap/vue-3';
import type { TiptapCollabProvider } from '@hocuspocus/provider';
import type { Doc as YDoc } from 'yjs';
import { TableColumnMenu, TableRowMenu } from '~/components/editor/extensions/Table/menus';
import ImageBlockMenu from './extensions/ImageBlock/components/ImageBlockMenu.vue';
import { LinkMenu, TextMenu, ContentItemMenu } from './extensions/menus';

interface ContentEditorProps {
  ydoc: YDoc;
  provider?: TiptapCollabProvider | null;
  userId?: string;
  userName?: string;
}

const props = defineProps<ContentEditorProps>();
const menuContainerRef = useTemplateRef<HTMLElement>("menuContainerRef");

const { editor, users, collabState } = useBlockEditor({
  ydoc: props.ydoc,
  provider: props.provider,
  userId: props.userId,
  userName: props.userName,
});

onBeforeUnmount(() => {
  if (editor?.value) {
    editor.value.destroy();
  }
});

onMounted(() => {
  console.log("Menu container:", menuContainerRef.value);
  console.log("Users:", users);
  console.log("Collab state:", collabState);
});
</script>

<style>
@import "styles/editor.scss";
</style>

<template>
  <div class="relative prose prose-zinc dark:prose-invert focus:outline-none mx-2">
    <TableColumnMenu v-if="editor" :editor="editor" />
    <TableRowMenu v-if="editor" :editor="editor" />
    <EditorContent :editor="editor" class="relative flex-1"/>
  </div>
</template>

<script setup lang="ts">
import { ref, onBeforeUnmount, defineProps } from 'vue';
import { EditorContent } from '@tiptap/vue-3';
import type { TiptapCollabProvider } from '@hocuspocus/provider';
import type { Doc as YDoc } from 'yjs';
import { TableColumnMenu, TableRowMenu } from '@/components/editor/extensions/v1/Table/menus';

interface ContentEditorProps {
  ydoc: YDoc;
  provider?: TiptapCollabProvider | null;
  userId?: string;
  userName?: string;
}

const props = defineProps<ContentEditorProps>();

const { editor, users, collabState } = useBlockEditor({
  ydoc: props.ydoc,
  provider: props.provider,
  userId: props.userId,
  userName: props.userName,
});

onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
  }
});
</script>

<style>
@import "styles/editor.scss";
</style>

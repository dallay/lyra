<template>
  <div
    class="prose prose-sm sm:prose lg:prose-lg xl:prose-2xl focus:outline-none mx-2"
  >
    <EditorContent :editor="editor" />
  </div>
</template>

<script setup lang="ts">
import { useEditor, EditorContent } from '@tiptap/vue-3';
import Highlight from '@tiptap/extension-highlight';
import Typography from '@tiptap/extension-typography';
import StarterKit from '@tiptap/starter-kit';
import Placeholder from '@tiptap/extension-placeholder';
import Code from '@tiptap/extension-code';
import Document from '@tiptap/extension-document';
import Paragraph from '@tiptap/extension-paragraph';
import Text from '@tiptap/extension-text';

import { ColorHighlighter } from './extensions/ColorHighlighter';
import { SmilieReplacer } from './extensions/SmilieReplacer';

import Commands from './command/commands';
import suggestion from './command/suggestion';

const editor = useEditor({
  extensions: [
    StarterKit,
    Document,
    Paragraph,
    Code,
    Text,
    Highlight,
    Typography,
    Placeholder.configure({
      placeholder: ({ node }) => {
        if (node.type.name === 'paragraph' && node.textContent === '') {
          return 'Type / to browse options';
        }
        return 'Click here to start writing...';
      },
    }),
    ColorHighlighter,
    SmilieReplacer,
    Commands.configure({
      suggestion,
    }),
  ],
  content: '<p></p>',
  editorProps: {
    attributes: {
      class: 'prose prose-sm sm:prose lg:prose-lg xl:prose-2xl focus:outline-none',
    },
  },
});

onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
  }
});
</script>

<style lang="scss">
@import './editor.scss';
</style>

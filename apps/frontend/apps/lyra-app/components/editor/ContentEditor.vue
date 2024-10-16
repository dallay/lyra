<template>
  <div
    class="prose prose-zinc dark:prose-invert focus:outline-none mx-2"
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
import Image from '@tiptap/extension-image';
import Dropcursor from '@tiptap/extension-dropcursor';
import Code from '@tiptap/extension-code';
import Document from '@tiptap/extension-document';
import Paragraph from '@tiptap/extension-paragraph';
import Text from '@tiptap/extension-text';
import Table from '@tiptap/extension-table';
import TableCell from '@tiptap/extension-table-cell';
import TableHeader from '@tiptap/extension-table-header';
import TableRow from '@tiptap/extension-table-row';
import Youtube from '@tiptap/extension-youtube';

import Link from '@tiptap/extension-link';

import { ColorHighlighter } from './extensions/ColorHighlighter';
import { SmilieReplacer } from './extensions/SmilieReplacer';

import Commands from './command/commands';
import suggestion from './command/suggestion';

import ImageUploader from './extensions/embed/image/uploader/ImageUploader';
import YouTubeUploader from './extensions/embed/youtube/YouTubeUploader';
import EmbedLink from './extensions/embed/link/EmbedLink';

const CustomTableCell = TableCell.extend({
  addAttributes() {
    return {
      ...this.parent?.(),
      backgroundColor: {
        default: null,
        parseHTML: (element) => element.getAttribute('data-background-color'),
        renderHTML: (attributes) => {
          return {
            'data-background-color': attributes.backgroundColor,
            style: `background-color: ${attributes.backgroundColor}`,
          };
        },
      },
    };
  },
});

const editor = useEditor({
  extensions: [
    StarterKit,
    Document,
    Paragraph,
    Code,
    Text,
    Highlight,
    Typography,
    Image,
    ImageUploader,
    Dropcursor,
    Table.configure({
      resizable: true,
    }),
    TableRow,
    TableHeader,
    CustomTableCell,
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
    Youtube.configure({
      controls: false,
      nocookie: true,
    }),
    YouTubeUploader,
    EmbedLink,
    Link,
  ],
  content: `<p></p>`,
  editorProps: {
    attributes: {
      class: 'prose prose-zinc dark:prose-invert focus:outline-none',
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
@import "./editor.scss";
</style>

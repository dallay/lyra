<template>
  <div
    class="prose prose-sm sm:prose lg:prose-lg xl:prose-2xl focus:outline-none mx-2"
  >
    <EditorContent :editor="editor" />
  </div>
</template>

<script setup lang="ts">
import { useEditor, EditorContent } from "@tiptap/vue-3";
import Highlight from "@tiptap/extension-highlight";
import Typography from "@tiptap/extension-typography";
import StarterKit from "@tiptap/starter-kit";
import Placeholder from "@tiptap/extension-placeholder";
import Code from "@tiptap/extension-code";
import Document from "@tiptap/extension-document";
import Paragraph from "@tiptap/extension-paragraph";
import Text from "@tiptap/extension-text";

import { ColorHighlighter } from "./extensions/ColorHighlighter";
import { SmilieReplacer } from "./extensions/SmilieReplacer";

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
        if (node.type.name === "paragraph" && node.textContent === "") {
          return "Type / to browse options";
        }
        return "Click here to start writing...";
      },
    }),
    ColorHighlighter,
    SmilieReplacer,
  ],
  content: "",
  editorProps: {
    attributes: {
      class:
        "prose prose-sm sm:prose lg:prose-lg xl:prose-2xl focus:outline-none",
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
/* Basic editor styles */
.tiptap {
  :first-child {
    margin-top: 0;
  }

  /* Code and preformatted text styles */
  code {
    background-color: var(--purple-light);
    border-radius: 0.4rem;
    color: var(--black);
    font-size: 0.85rem;
    padding: 0.25em 0.3em;
  }

  /* Color swatches */
  .color {
    white-space: nowrap;

    &::before {
      background-color: var(--color);
      border: 1px solid rgba(128, 128, 128, 0.3);
      border-radius: 2px;
      content: " ";
      display: inline-block;
      height: 1em;
      margin-bottom: 0.15em;
      margin-right: 0.1em;
      vertical-align: middle;
      width: 1em;
    }
  }
}

/* Placeholder (at the top) */
.is-empty::before {
  color: #9ca3af; /* Tailwind CSS gray-400 */
  content: attr(data-placeholder);
  float: left;
  height: 0;
  pointer-events: none;
  font-style: italic; /* Optional: to make the placeholder text italic */
}
</style>

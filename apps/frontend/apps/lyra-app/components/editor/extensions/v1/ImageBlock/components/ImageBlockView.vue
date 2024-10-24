<template>
  <NodeViewWrapper>
    <div :class="wrapperClassName" :style="{ width: node.attrs.width }">
      <div contenteditable="false" ref="imageWrapperRef">
        <img class="block" :src="node.attrs.src" alt="" @click="onClick" />
      </div>
    </div>
  </NodeViewWrapper>
</template>

<script setup lang="ts">
import { ref, computed, defineProps } from 'vue';
import { NodeViewWrapper } from '@tiptap/vue-3';
import { Editor, Node } from '@tiptap/core';

interface ImageBlockViewProps {
  editor: Editor;
  getPos: () => number;
  node: Node & {
    attrs: {
      src: string;
      align: string;
      width: string;
    };
  };
  updateAttributes: (attrs: Record<string, string>) => void;
}

const props = defineProps<ImageBlockViewProps>();

const imageWrapperRef = ref<HTMLDivElement | null>(null);

const wrapperClassName = computed(() => {
  return [
    props.node.attrs.align === 'left' ? 'ml-0' : 'ml-auto',
    props.node.attrs.align === 'right' ? 'mr-0' : 'mr-auto',
    props.node.attrs.align === 'center' && 'mx-auto',
  ].filter(Boolean).join(' ');
});

const onClick = () => {
  props.editor.commands.setNodeSelection(props.getPos());
};
</script>

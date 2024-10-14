import { type Component } from 'vue';
import { mergeAttributes, Node, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';

import ImageUploader from './ImageUploader.vue';

export default Node.create({
  name: 'image-uploader',

  group: 'block',

  atom: true,

  addAttributes() {
    return {
      src: {
        default: null,
      },
    };
  },

  parseHTML() {
    return [
      {
        tag: 'image-uploader',
      },
    ];
  },

  renderHTML({ HTMLAttributes }: { HTMLAttributes: Record<string, any> }) {
    return ['image-uploader', mergeAttributes(HTMLAttributes)];
  },

  addNodeView() {
    return VueNodeViewRenderer(ImageUploader as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

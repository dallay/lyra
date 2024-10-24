import { type Component } from 'vue';
import { mergeAttributes, Node, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';

import ImageUploaderComponent from './ImageUploader.vue';
declare module '@tiptap/core' {
  interface Commands<ReturnType> {
    imageUpload: {
      setImageUpload: () => ReturnType
    }
  }
}

export const ImageUploader = Node.create({
  name: 'image-uploader',

  isolating: true,

  defining: true,

  group: 'block',

  draggable: true,

  selectable: true,

  inline: false,

  parseHTML() {
    return [
      {
        tag: `div[data-type="${this.name}"]`,
      },
    ]
  },

  renderHTML() {
    return ['div', { 'data-type': this.name }]
  },

  addCommands() {
    return {
      setImageUpload:
        () =>
        ({ commands }) =>
          commands.insertContent(`<div data-type="${this.name}"></div>`),
    }
  },

  addNodeView() {
    return VueNodeViewRenderer(ImageUploaderComponent as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

export default ImageUploader
;

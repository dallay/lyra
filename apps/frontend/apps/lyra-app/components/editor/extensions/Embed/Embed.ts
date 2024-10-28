import { type Component } from 'vue';
import { mergeAttributes, Node, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';

import EmbedComponent from './Embed.vue';
import type { SupportedEmbeds } from './types';

declare module '@tiptap/core' {
  interface Commands<ReturnType> {
    embedBlock: {
      setEmbed: (attributes: {url: string, embedType: SupportedEmbeds}) => ReturnType,
    }
  }
}


export const EmbedNode = Node.create({
  name: 'embed',

  isolating: true,

  defining: true,

  group: 'block',

  draggable: true,

  selectable: true,

  atom: true,

  addAttributes() {
    return {
      url: {
        default: null,
      },
      embedType: {
        default: 'link',
      },
    };
  },

  parseHTML() {
    return [
      {
        tag: 'embed',
      },
    ];
  },

  renderHTML({ HTMLAttributes }: { HTMLAttributes: Record<string, any> }) {
    return ['embed', mergeAttributes(HTMLAttributes)];
  },

  addCommands() {
    return {
      setEmbed: attrs =>
        ({ commands }) => {
          return commands.insertContent({
            type: this.name,
            attrs,
          });
      },
    };
  },

  addNodeView() {
    return VueNodeViewRenderer(EmbedComponent as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

export default EmbedNode;

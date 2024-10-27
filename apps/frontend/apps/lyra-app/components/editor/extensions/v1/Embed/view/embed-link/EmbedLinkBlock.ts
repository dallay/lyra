import { type Component } from 'vue';
import { mergeAttributes, Node, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';

import EmbedLinkBlockComponent from './EmbedLinkBlockV1.vue';
import type { EmbedMetadata } from '../../types';

type LayoutOption = 'right' | 'left' | 'top'

declare module '@tiptap/core' {
  interface Commands<ReturnType> {
    embedLinkBlock: {
      setEmbedLinkBlock: (attributes: EmbedMetadata & { layout: LayoutOption }) => ReturnType,
      setEmbedLinkLayout: (layout: LayoutOption) => ReturnType,
    }
  }
}

export const EmbedLinkBlock = Node.create({
  name: 'embedLink',

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
      image: {
        default: '',
      },
      title: {
        default: '',
      },
      description: {
        default: '',
      },
      layout: {
        default: 'left',
        parseHTML: element => element.getAttribute('data-layout'),
        renderHTML: attributes => ({
          'data-layout': attributes.layout,
        }),
      },
    };
  },

  parseHTML() {
    return [
      {
        tag: 'embedLink',
      },
    ];
  },

  renderHTML({ HTMLAttributes }: { HTMLAttributes: Record<string, any> }) {
    return ['embedLink', mergeAttributes(HTMLAttributes)];
  },

  addCommands() {
    return {
      setEmbedLinkBlock: attrs =>
        ({ commands }) => {
          return commands.insertContent({
            type: this.name,
            attrs,
          });
      },
      setEmbedLinkLayout: layout =>
        ({ commands }) =>
          commands.updateAttributes('embedLink', { layout }),
    };
  },

  addNodeView() {
    return VueNodeViewRenderer(EmbedLinkBlockComponent as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

export default EmbedLinkBlock;

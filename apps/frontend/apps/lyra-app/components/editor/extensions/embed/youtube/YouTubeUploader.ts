import { type Component } from 'vue';
import { mergeAttributes, Node, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';

import YouTubeUploader from './YouTubeUploaderV2.vue';

export default Node.create({
  name: 'youtube-uploader',

  group: 'block',

  atom: true,

  addAttributes() {
    return {
      url: {
        default: null,
      },
    };
  },

  parseHTML() {
    return [
      {
        tag: 'youtube-uploader',
      },
    ];
  },

  renderHTML({ HTMLAttributes }: { HTMLAttributes: Record<string, any> }) {
    return ['youtube-uploader', mergeAttributes(HTMLAttributes)];
  },

  addNodeView() {
    return VueNodeViewRenderer(YouTubeUploader as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

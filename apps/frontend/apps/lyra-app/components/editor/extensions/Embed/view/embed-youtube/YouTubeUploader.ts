import { type Component } from 'vue';
import { mergeAttributes, Node, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';

import YouTubeUploaderComponent from './YouTubeUploader.vue';

export const YouTubeUploader = Node.create({
  name: 'youtubeUploader',

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
        tag: 'youtubeUploader',
      },
    ];
  },

  renderHTML({ HTMLAttributes }: { HTMLAttributes: Record<string, any> }) {
    return ['youtubeUploader', mergeAttributes(HTMLAttributes)];
  },

  addNodeView() {
    return VueNodeViewRenderer(YouTubeUploaderComponent as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

export default YouTubeUploader;

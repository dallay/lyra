import { Node, mergeAttributes, type NodeConfig, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import EditorButton from './EditorButton.vue';
import type { ButtonVariants } from '~/components/ui/button';

interface EditorButtonProps {
  label: string;
  variant?: ButtonVariants['variant']
  size?: ButtonVariants['size']
}

export default Node.create({
  name: 'editor-button',

  group: 'block',

  atom: true,

  addAttributes() {
    return {
      label: {
        default: 'Click me',
      },
      variant: {
        default: 'primary',
      },
      size: {
        default: 'medium',
      },
    };
  },

  parseHTML() {
    return [
      {
        tag: 'editor-button',
      },
    ];
  },

  renderHTML({ HTMLAttributes }: { HTMLAttributes: Record<string, any> }) {
    return ['editor-button', mergeAttributes(HTMLAttributes)];
  },

  addNodeView() {
    return VueNodeViewRenderer(EditorButton as unknown as Component<NodeViewProps>);
  },
} as NodeConfig);

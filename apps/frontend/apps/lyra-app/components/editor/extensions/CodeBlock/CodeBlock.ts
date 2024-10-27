import { type Component } from 'vue'
import { CodeBlockLowlight } from '@tiptap/extension-code-block-lowlight'
import { all, createLowlight } from 'lowlight'
import { VueNodeViewRenderer, type NodeViewProps } from '@tiptap/vue-3'

import CodeBlockComponent from './CodeBlockComponent.vue'

const lowlight = createLowlight(all)

export const CodeBlock = CodeBlockLowlight
  .extend({
    addNodeView() {
      return VueNodeViewRenderer(CodeBlockComponent as unknown as Component<NodeViewProps>)
    },
  })
  .configure({
    lowlight,
    defaultLanguage: 'javascript',
  })

import { VueNodeViewRenderer } from '@tiptap/vue-3'
import { mergeAttributes, type NodeViewProps, type Range, Node } from '@tiptap/core'
import type { Component } from 'vue'
import TableOfContentsNodeView from './TableOfContentsNode.vue'

declare module '@tiptap/core' {
  interface Commands<ReturnType> {
    tableOfContentsNode: {
      insertTableOfContents: () => ReturnType
    }
  }
}

export const TableOfContentsNode = Node.create({
  name: 'tableOfContentsNode',
  group: 'block',
  atom: true,
  selectable: true,
  draggable: true,
  inline: false,

  parseHTML() {
    return [
      {
        tag: 'div[data-type="table-of-content"]',
      },
    ]
  },

  renderHTML({ HTMLAttributes }) {
    return ['div', { ...HTMLAttributes, 'data-type': 'table-of-content' }]
  },

  addNodeView() {
    return VueNodeViewRenderer(TableOfContentsNodeView as unknown as Component<NodeViewProps>)
  },

  addCommands() {
    return {
      insertTableOfContents:
        () =>
          ({ commands }) => {
            return commands.insertContent({
              type: this.name,
            })
          },
    }
  },
})

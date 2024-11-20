import type { Editor } from '@tiptap/vue-3'
import type { Node } from '@tiptap/pm/model'
import type { NodeSelection } from '@tiptap/pm/state'

export default function useContentItemActions(editor: Editor, currentNode: Node | null, currentNodePos: number) {
  const resetTextFormatting = () => {
    const chain = editor.chain()

    chain.setNodeSelection(currentNodePos).unsetAllMarks()

    if (currentNode?.type.name !== 'paragraph') {
      chain.setParagraph()
    }

    chain.run()
  }

  const duplicateNode = () => {
    editor.commands.setNodeSelection(currentNodePos)

    const { $anchor } = editor.state.selection
    const selectedNode = $anchor.node(1) || (editor.state.selection as NodeSelection).node

    editor
      .chain()
      .setMeta('hideDragHandle', true)
      .insertContentAt(currentNodePos + (currentNode?.nodeSize || 0), selectedNode.toJSON())
      .run()
  }

  const copyNodeToClipboard = async () => {
    editor.chain().setMeta('hideDragHandle', true).setNodeSelection(currentNodePos).run()

    try {
      const selectedNode = editor.state.selection.content().content
      const text = selectedNode.textBetween(0, selectedNode.size, '\n')
      await navigator.clipboard.writeText(text)
    } catch (err) {
      console.error('Failed to copy: ', err)
    }
  }

  const deleteNode = () => {
    editor.chain().setMeta('hideDragHandle', true).setNodeSelection(currentNodePos).deleteSelection().run()
  }

  const handleAdd = () => {
    if (currentNodePos !== -1) {
      const currentNodeSize = currentNode?.nodeSize || 0
      const insertPos = currentNodePos + currentNodeSize
      const currentNodeIsEmptyParagraph = currentNode?.type.name === 'paragraph' && currentNode?.content?.size === 0
      const focusPos = currentNodeIsEmptyParagraph ? currentNodePos + 2 : insertPos + 2

      editor
        .chain()
        .command(({ dispatch, tr, state }) => {
          if (dispatch) {
            if (currentNodeIsEmptyParagraph) {
              tr.insertText('/', currentNodePos, currentNodePos + 1)
            } else {
              tr.insert(insertPos, state.schema.nodes.paragraph.create(null, [state.schema.text('/')]))
            }

            return dispatch(tr)
          }

          return true
        })
        .focus(focusPos)
        .run()
    }
  }

  return {
    resetTextFormatting,
    duplicateNode,
    copyNodeToClipboard,
    deleteNode,
    handleAdd
  }
}

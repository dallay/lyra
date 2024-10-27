import type { Editor } from "@tiptap/core"
import type { EditorState } from "@tiptap/pm/state"
import type { EditorView } from "@tiptap/pm/view"
import { isTableSelected } from "../../utils"
import Table from "@tiptap/extension-table"


export const isColumnGripSelected = ({
  editor,
  view,
  state,
  from,
}: {
  editor: Editor
  view: EditorView
  state: EditorState
  from: number
}) => {
  const domAtPos = view.domAtPos(from).node as HTMLElement
  const nodeDOM = view.nodeDOM(from) as HTMLElement
  const node = nodeDOM || domAtPos

  if (!editor.isActive(Table.name) || !node || isTableSelected(state.selection)) {
    return false
  }

  let container = node

  while (container && !['TD', 'TH'].includes(container.tagName)) {
    container = container.parentElement!
  }

  const gripColumn = container && container.querySelector && container.querySelector('a.grip-column.selected')

  return !!gripColumn
}

export default isColumnGripSelected


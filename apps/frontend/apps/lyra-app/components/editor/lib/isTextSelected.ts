import { isTextSelection } from '@tiptap/core'
import { Editor } from '@tiptap/vue-3'

export const isTextSelected = ({ editor }: { editor: Editor }) => {
  const {
    state: {
      doc,
      selection,
      selection: { empty, from, to },
    },
  } = editor

  const isEmptyTextBlock = !doc.textBetween(from, to).length && isTextSelection(selection)

  if (empty || isEmptyTextBlock || !editor.isEditable) {
    return false
  }

  return true
}

export default isTextSelected

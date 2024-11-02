import { reactive, watch, ref } from 'vue'
import { Editor } from '@tiptap/vue-3'
import { isCustomNodeSelected, isTextSelected } from '@/components/editor/lib/'
import type { EditorView } from '@tiptap/pm/view'

export function useTextMenuStates(editor: Editor) {
  const states = ref({
    isBold: false,
    isItalic: false,
    isStrike: false,
    isUnderline: false,
    isCode: false,
    isSubscript: false,
    isSuperscript: false,
    isAlignLeft: false,
    isAlignCenter: false,
    isAlignRight: false,
    isAlignJustify: false,
    currentColor: undefined,
    currentHighlight: undefined,
    currentFont: undefined,
    currentSize: undefined,
  })

  const updateStates = () => {
    states.value.isBold = editor.isActive('bold')
    states.value.isItalic = editor.isActive('italic')
    states.value.isStrike = editor.isActive('strike')
    states.value.isUnderline = editor.isActive('underline')
    states.value.isCode = editor.isActive('code')
    states.value.isSubscript = editor.isActive('subscript')
    states.value.isSuperscript = editor.isActive('superscript')
    states.value.isAlignLeft = editor.isActive({ textAlign: 'left' })
    states.value.isAlignCenter = editor.isActive({ textAlign: 'center' })
    states.value.isAlignRight = editor.isActive({ textAlign: 'right' })
    states.value.isAlignJustify = editor.isActive({ textAlign: 'justify' })
    states.value.currentColor = editor.getAttributes('textStyle')?.color || undefined
    states.value.currentHighlight = editor.getAttributes('highlight')?.color || undefined
    states.value.currentFont = editor.getAttributes('textStyle')?.fontFamily || undefined
    states.value.currentSize = editor.getAttributes('textStyle')?.fontSize || undefined
  }

  watch(
    () => editor.view?.state,
    () => {
      updateStates()
    },
    { immediate: true }
  )

  const shouldShow = ({ view, from }: {view:EditorView, from:number}) => {
    if (!view || editor.view.dragging) {
      return false
    }

    const domAtPos = view.domAtPos(from || 0).node
    const nodeDOM = view.nodeDOM(from || 0)
    const node = nodeDOM instanceof HTMLElement ? nodeDOM : domAtPos instanceof HTMLElement ? domAtPos : null

    if (node && isCustomNodeSelected(editor, node)) {
      return false
    }

    return isTextSelected({ editor })
  }

  return {
    shouldShow,
    states,
  }
}

import { reactive, watch } from 'vue'
import { Editor } from '@tiptap/vue-3'
import { isCustomNodeSelected, isTextSelected } from '@/components/editor/lib/'
import type { EditorView } from '@tiptap/pm/view'

export function useTextMenuStates(editor: Editor) {
  const states = reactive({
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
    states.isBold = editor.isActive('bold')
    states.isItalic = editor.isActive('italic')
    states.isStrike = editor.isActive('strike')
    states.isUnderline = editor.isActive('underline')
    states.isCode = editor.isActive('code')
    states.isSubscript = editor.isActive('subscript')
    states.isSuperscript = editor.isActive('superscript')
    states.isAlignLeft = editor.isActive({ textAlign: 'left' })
    states.isAlignCenter = editor.isActive({ textAlign: 'center' })
    states.isAlignRight = editor.isActive({ textAlign: 'right' })
    states.isAlignJustify = editor.isActive({ textAlign: 'justify' })
    states.currentColor = editor.getAttributes('textStyle')?.color || undefined
    states.currentHighlight = editor.getAttributes('highlight')?.color || undefined
    states.currentFont = editor.getAttributes('textStyle')?.fontFamily || undefined
    states.currentSize = editor.getAttributes('textStyle')?.fontSize || undefined
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
    ...toRefs(states),
  }
}

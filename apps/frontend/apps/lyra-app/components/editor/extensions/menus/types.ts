import type { Editor as CoreEditor } from '@tiptap/core'
import type { Editor } from '@tiptap/vue-3'
import type { EditorState } from '@tiptap/pm/state'
import type { EditorView } from '@tiptap/pm/view'

export interface MenuProps {
  editor: Editor
  appendTo?: HTMLElement
  shouldHide?: boolean
}

export interface ShouldShowProps {
  editor: CoreEditor
  view: EditorView
  state?: EditorState
  oldState?: EditorState
  from?: number
  to?: number
}

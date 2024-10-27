import type { Ref } from 'vue'
import { Editor as CoreEditor } from '@tiptap/core'
import { Editor } from '@tiptap/vue-3'
import { EditorState } from '@tiptap/pm/state'
import { EditorView } from '@tiptap/pm/view'

export interface MenuProps {
  editor: Editor
  appendTo?: Ref<any>
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

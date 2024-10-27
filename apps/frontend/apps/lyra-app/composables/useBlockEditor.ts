import { ref, computed, onMounted } from 'vue'

declare global {
  interface Window {
    editor: ReturnType<typeof useEditor> | null;
  }
}
import { useEditor, type AnyExtension } from '@tiptap/vue-3'
import Collaboration from '@tiptap/extension-collaboration'
import CollaborationCursor from '@tiptap/extension-collaboration-cursor'
import { TiptapCollabProvider, WebSocketStatus } from '@hocuspocus/provider'
import type { Doc as YDoc } from 'yjs'

import { ExtensionKit } from '@/components/editor/extensions/v1/extension-kit'
import { userColors, userNames } from '@/components/editor/lib/constants'
import { randomElement } from '@lyra/utilities'
import { initialContent } from '~/components/editor/lib/initialContent'

interface EditorUser {
  id: string
  name: string
}

interface UseBlockEditorProps {
  ydoc: YDoc
  provider?: TiptapCollabProvider | null
  userId?: string
  userName?: string
}

export const useBlockEditor = ({
  ydoc,
  provider,
  userId,
  userName = 'Yuniel',
}: UseBlockEditorProps) => {
  const collabState = ref<WebSocketStatus>(
    provider ? WebSocketStatus.Connecting : WebSocketStatus.Disconnected
  )

  const editor = useEditor({
    content: initialContent,
    autofocus: true,
    onCreate: ctx => {
      if (provider && !provider.isSynced) {
        provider.on('synced', () => {
          setTimeout(() => {
            if (ctx.editor.isEmpty) {
              ctx.editor.commands.setContent(initialContent)
            }
          }, 0)
        })
      } else if (ctx.editor.isEmpty) {
        ctx.editor.commands.setContent(initialContent)
        ctx.editor.commands.focus('start', { scrollIntoView: true })
      }
    },
    extensions: [
      ...ExtensionKit({
        provider,
      }),
      provider
        ? Collaboration.configure({
            document: ydoc,
          })
        : undefined,
      provider
        ? CollaborationCursor.configure({
            provider,
            user: {
              name: randomElement(userNames),
              color: randomElement(userColors),
            },
          })
        : undefined,
    ].filter((e): e is AnyExtension => e !== undefined),
    editorProps: {
      attributes: {
        autocomplete: 'off',
        autocorrect: 'off',
        autocapitalize: 'off',
        class: 'min-h-full prose prose-zinc dark:prose-invert focus:outline-none',
      },
    },
  })

  // Computed property para obtener los usuarios conectados
  const users = computed(() => {
    const collaborationCursor = editor.value?.storage.collaborationCursor
    if (!collaborationCursor?.users) {
      return []
    }
    return collaborationCursor.users.map((user: EditorUser) => {
      const names = user.name?.split(' ')
      const initials = `${names?.[0]?.[0] || '?'}${names?.[names.length - 1]?.[0] || '?'}`
      return { ...user, initials: initials.length ? initials : '?' }
    })
  })


  onMounted(() => {
    provider?.on('status', (event: { status: WebSocketStatus }) => {
      collabState.value = event.status
    })
  })

  if (typeof window !== 'undefined') {
    window.editor = editor
  }

  return {
    editor,
    users,
    collabState,
  }
}

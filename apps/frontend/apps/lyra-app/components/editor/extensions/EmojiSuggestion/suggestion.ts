import { VueRenderer } from '@tiptap/vue-3';
import tippy, { type Instance, type Props } from 'tippy.js';
import { Editor } from '@tiptap/core';
import type { SuggestionKeyDownProps, SuggestionProps } from '@tiptap/suggestion';

import EmojiList from './components/EmojiList.vue'

export const emojiSuggestion = {
  items: ({ editor, query }: { editor: Editor; query: string }) =>
    editor.storage.emoji.emojis
      .filter(
        ({ shortcodes, tags }: { shortcodes: string[]; tags: string[] }) =>
          shortcodes.find(shortcode => shortcode.startsWith(query.toLowerCase())) ||
          tags.find(tag => tag.startsWith(query.toLowerCase())),
      )
      .slice(0, 250),

  allowSpaces: false,

  render: () => {
    let component: VueRenderer;
    let popup: Instance<Props>;

    return {
      onStart: (props: SuggestionProps) => {
        component = new VueRenderer(EmojiList, {
          props,
          editor: props.editor,
        })

        if(component?.element)
        popup = tippy(document.body,  {
          getReferenceClientRect: props.clientRect as () => DOMRect,
          appendTo: () => document.body,
          content: component.element,
          showOnCreate: true,
          interactive: true,
          trigger: 'manual',
          placement: 'bottom-start',
        })
      },

      onUpdate(props: SuggestionProps) {
        component.updateProps(props)

        popup.setProps({
          getReferenceClientRect: props.clientRect as () => DOMRect,
        })
      },

      onKeyDown(props: SuggestionKeyDownProps) {
        if (props.event.key === 'Escape') {
          popup.hide()
          component.destroy()

          return true
        }

        return component.ref?.onKeyDown(props) ?? false
      },

      onExit() {
        popup.destroy()
        component.destroy()
      },
    }
  },
}

export default emojiSuggestion

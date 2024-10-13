import { Extension } from '@tiptap/core';
import Suggestion, { type SuggestionOptions } from '@tiptap/suggestion';
import { Editor } from '@tiptap/core';

interface CommandProps {
  editor: Editor;
  range: { from: number; to: number };
  props: { command: (args: { editor: Editor; range: { from: number; to: number } }) => void };
}

export default Extension.create({
  name: 'commands',

  addOptions() {
    return {
      suggestion: {
        char: '/',
        command: ({ editor, range, props }: CommandProps) => {
          props.command({ editor, range });
        },
      } as SuggestionOptions,
    };
  },

  addProseMirrorPlugins() {
    return [
      Suggestion({
        editor: this.editor,
        ...this.options.suggestion,
      }),
    ];
  },
});

import { Extension } from '@tiptap/core';
import Suggestion, { type SuggestionOptions } from '@tiptap/suggestion';
import { Editor } from '@tiptap/core';
import { PluginKey } from '@tiptap/pm/state';
import type { Command } from './types';

interface CommandProps {
  editor: Editor;
  range: { from: number; to: number };
  props: Command;
}

const extensionName = 'slashCommand'

export const SlashCommand =  Extension.create({
  name: extensionName,

  priority: 200,

  addOptions() {
    return {
      suggestion: {
        char: '/',
        allowSpaces: true,
        startOfLine: true,
        pluginKey: new PluginKey(extensionName),
        command: ({ editor, range, props }: CommandProps) => {
          props.action({ editor, range });
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

export default SlashCommand;

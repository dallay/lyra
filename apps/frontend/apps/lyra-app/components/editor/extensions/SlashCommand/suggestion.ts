import { VueRenderer } from '@tiptap/vue-3';
import tippy, { type Instance, type Props } from 'tippy.js';
import { Editor, type Range } from '@tiptap/core';
import CommandsList from './CommandsList.vue';
import { type Component } from 'vue';
import type { CommandActionProps, Group } from './types';

import { GROUPS } from './groups'

interface SuggestionProps {
  query: string;
  editor: Editor;
  range: Range;
  clientRect: () => DOMRect;
  items: Group[];
  command: (props: CommandActionProps) => void;
}

export const SlashCommandSuggestion = {
  items: ({ query, editor }: { query: string, editor: Editor }): Group[] => {
    // const allGroups: Group[] = GROUPS;

    // return allGroups
    //   .map((commandGroup) => ({
    //     ...commandGroup,
    //     items: commandGroup.commands.filter((commandItem) =>
    //       commandItem.name.toLowerCase().includes(query.toLowerCase()) ||
    //       commandItem.label.toLowerCase().includes(query.toLowerCase()) ||
    //       (commandItem.aliases &&
    //         commandItem.aliases.some((alias) => alias.toLowerCase().includes(query.toLowerCase()))),
    //     ),
    //   }))
    //   .filter((group) => group.items.length > 0);
    const withFilteredCommands = GROUPS.map(group => ({
      ...group,
      commands: group.commands
        .filter(item => {
          const labelNormalized = item.label.toLowerCase().trim()
          const queryNormalized = query.toLowerCase().trim()

          if (item.aliases) {
            const aliases = item.aliases.map(alias => alias.toLowerCase().trim())

            return labelNormalized.includes(queryNormalized) || aliases.includes(queryNormalized)
          }

          return labelNormalized.includes(queryNormalized)
        })
        .filter(command => (command.shouldBeHidden ? !command.shouldBeHidden(editor) : true)),
    }))

    const withoutEmptyGroups = withFilteredCommands.filter(group => {
      if (group.commands.length > 0) {
        return true
      }

      return false
    })

    const withEnabledSettings = withoutEmptyGroups.map(group => ({
      ...group,
      commands: group.commands.map(command => ({
        ...command,
        isEnabled: true,
      })),
    }))

    return withEnabledSettings
  },

  render: () => {
    let component: VueRenderer | null = null;
    let popup: Instance<Props> | null = null;

    return {
      onStart: (props: SuggestionProps) => {
        component = new VueRenderer(CommandsList, {
          props: {
            items: props.items,
            command: props.command,
          },
          editor: props.editor,
        });

        if (!props.clientRect) {
          return;
        }

        if (component?.element) {
          popup = tippy(document.body, {
            getReferenceClientRect: props.clientRect,
            appendTo: () => document.body,
            content: component.element,
            showOnCreate: true,
            interactive: true,
            trigger: 'manual',
            placement: 'bottom-start',
          });
        }
      },

      onUpdate(props: SuggestionProps) {
        component?.updateProps(props);

        if (!props.clientRect) {
          return;
        }

        popup?.setProps({
          getReferenceClientRect: props.clientRect,
        });
      },

      onKeyDown(props: { event: KeyboardEvent }) {
        if (props.event.key === 'Escape') {
          popup?.hide();
          return true;
        }

        return component?.ref?.onKeyDown(props.event) ?? false;
      },

      onExit() {
        popup?.destroy();
        component?.destroy();
      },
    };
  },
};

export default SlashCommandSuggestion;

import { Editor, type Range } from '@tiptap/core';

import { icons } from 'lucide-vue-next'

export interface Group {
  name: string
  title: string
  commands: Command[]
}

export interface CommandActionProps {
  editor: Editor;
  range: Range;
}

export interface Command {
  name: string
  label: string
  description: string
  aliases?: string[]
  iconName: keyof typeof icons | string
  action: (props: CommandActionProps) => void
  shouldBeHidden?: (editor: Editor) => boolean
}

export interface MenuListProps {
  editor: Editor
  items: Group[]
  command: (command: Command) => void
}

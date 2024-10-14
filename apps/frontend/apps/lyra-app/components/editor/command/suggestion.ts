import { VueRenderer } from '@tiptap/vue-3';
import tippy, { type Instance, type Props } from 'tippy.js';
import { Editor, type Range } from '@tiptap/core';
import { Link, Heading1, Heading2, Heading3, Heading4, Heading5, Heading6 } from 'lucide-vue-next';

import CommandsList from './CommandsList.vue';
import { type Component } from 'vue';

interface CommandProps {
  editor: Editor;
  range: Range;
}

interface SuggestionItem {
  title: string;
  description?: string;
  icon?: Component | string;
  command: (props: CommandProps) => void;
}

interface SuggestionCategory {
  category: string;
  items: SuggestionItem[];
}

interface SuggestionProps {
  query: string;
  editor: Editor;
  range: Range;
  clientRect: () => DOMRect;
  items: SuggestionItem[];
  command: (props: CommandProps) => void;
}

export default {
  items: ({ query }: { query: string }): SuggestionCategory[] => {
    const allItems: SuggestionCategory[] = [
      {
        category: 'Basics',
        items: [
          {
            title: 'Bullet List',
            description: 'Create a bullet list',
            icon: 'lucide:list',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).toggleBulletList().run();
            },
          },
          {
            title: 'Order List',
            description: 'Create an ordered list',
            icon: 'lucide:list-ordered',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).toggleOrderedList().run();
            },
          },
          {
            title: 'Blockquote',
            description: 'Create a blockquote',
            icon: 'lucide:quote',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).toggleBlockquote().run();
            },
          },
          {
            title: 'Code Block',
            description: 'Create a code block',
            icon: 'lucide:code',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).toggleCodeBlock().run();
            },
          },
          {
            title: 'Divider',
            description: 'Create a divider',
            icon: 'lucide:minus',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setHorizontalRule().run();
            },
          },
          {
            title: 'Image',
            description: 'Insert an image',
            icon: 'lucide:image',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).insertContent({
                type: 'image-uploader',
              }).run();
            },
          },
                    {
            title: 'Table',
            description: 'Insert a table',
            icon: 'lucide:table',
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run();
            },
          },
        ],
      },
      {
        category: 'Headings',
        items: [
          {
            title: 'Heading 1',
            description: 'Large title text',
            icon: Heading1,
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setNode('heading', { level: 1 }).run();
            },
          },
          {
            title: 'Heading 2',
            description: 'Medium title text',
            icon: Heading2,
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setNode('heading', { level: 2 }).run();
            },
          },
          {
            title: 'Heading 3',
            description: 'Small title text',
            icon: Heading3,
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setNode('heading', { level: 3 }).run();
            },
          },
          {
            title: 'Heading 4',
            description: 'Extra small title text',
            icon: Heading4,
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setNode('heading', { level: 4 }).run();
            },
          },
          {
            title: 'Heading 5',
            description: 'Extra small title text',
            icon: Heading5,
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setNode('heading', { level: 5 }).run();
            },
          },
          {
            title: 'Heading 6',
            description: 'Extra small title text',
            icon: Heading6,
            command: ({ editor, range }: CommandProps) => {
              editor.chain().focus().deleteRange(range).setNode('heading', { level: 6 }).run();
            },
          },
        ],
      },
      {
        category: 'Embeds',
        items: [
          {
            title: 'Embed Link',
            description: 'Embed a hyperlink',
            icon: Link,
            command: ({ editor, range }: CommandProps) => {
              alert('This feature is not implemented yet');
            },
          },
          {
            title: 'YouTube',
            description: 'Embed a YouTube video',
            icon: 'si:youtube-fill',
            command: ({ editor, range }: CommandProps) => {
              alert('This feature is not implemented yet');
            },
          },
        ],
      },
    ];

    return allItems
      .map((category) => ({
        ...category,
        items: category.items.filter((item) =>
          item.title.toLowerCase().includes(query.toLowerCase()),
        ),
      }))
      .filter((category) => category.items.length > 0);
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

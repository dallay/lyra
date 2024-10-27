import type { CommandActionProps, Group } from './types'

export const GROUPS: Group[] = [
  {
    name: 'basics',
    title: 'Basics',
    commands: [
      {
        name: 'bullet-list',
        label: 'Bullet List',
        description: 'Create a bullet list',
        aliases: ['ul'],
        iconName: 'lucide:list',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).toggleBulletList().run()
        },
      },
      {
        name: 'order-list',
        label: 'Order List',
        description: 'Create an ordered list',
        aliases: ['ol'],
        iconName: 'lucide:list-ordered',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).toggleOrderedList().run()
        },
      },
      {
        name: 'blockquote',
        label: 'Blockquote',
        description: 'Create a blockquote',
        iconName: 'lucide:quote',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setBlockquote().run()
        },
      },
      {
        name: 'code-block',
        label: 'Code Block',
        description: 'Create a code block',
        iconName: 'lucide:code',
        shouldBeHidden: editor => editor.isActive('columns'),
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setCodeBlock().run()
        },
      },
      {
        name: 'divider',
        label: 'Divider',
        description: 'Create a divider',
        iconName: 'lucide:minus',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHorizontalRule().run();
        },
      },
      {
        name: 'image',
        label: 'Image',
        description: 'Insert an image',
        aliases: ['img'],
        iconName: 'lucide:image',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setImageUpload().run()
        },
      },
      {
        name: 'table',
        label: 'Table',
        description: 'Insert a table',
        iconName: 'lucide:table',
        shouldBeHidden: editor => editor.isActive('columns'),
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).insertTable({ rows: 3, cols: 3, withHeaderRow: false }).run();
        },
      },
      // {
      //   name: 'button',
      //   label: 'Button',
      //   description: 'Insert a button',
      //   iconName: 'ic:sharp-smart-button',
      //   action: ({ editor, range }: CommandActionProps) => {
      //     editor
      //     .chain()
      //     .focus().deleteRange(range)
      //     .insertContent({
      //       type: 'editor-button',
      //     })
      //     .run();
      //   },
      // },
     {
        name: 'columns',
        label: 'Columns',
        iconName: 'Columns2',
        description: 'Add two column content',
        aliases: ['cols'],
        shouldBeHidden: editor => editor.isActive('columns'),
        action: ({ editor, range }: CommandActionProps) => {
          editor
            .chain()
            .focus().deleteRange(range)
            .setColumns()
            .focus(editor.state.selection.head - 1)
            .run()
        },
      },
    ],
  },
  {
    name: 'headings',
    title: 'Headings',
    commands: [
      {
        name: 'heading1',
        label: 'Heading 1',
        description: 'High priority section title',
        aliases: ['h1'],
        iconName: 'lucide:heading-1',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHeading({ level: 1 }).run();
        },
      },
      {
        name: 'heading2',
        label: 'Heading 2',
        description: 'Medium priority section title',
        aliases: ['h2'],
        iconName: 'lucide:heading-2',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHeading({ level: 2 }).run();
        },
      },
      {
        name: 'heading3',
        label: 'Heading 3',
                description: 'Low priority section title',
        aliases: ['h3'],
        iconName: 'lucide:heading-3',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHeading({ level: 3 }).run();
        },
      },
      {
        name: 'heading4',
        label: 'Heading 4',
        description: 'Extra small title text',
        iconName: 'lucide:heading-4',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHeading({ level: 4 }).run();
        },
      },
      {
        name: 'heading5',
        label: 'Heading 5',
        description: 'Extra small title text',
        iconName: 'lucide:heading-5',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHeading({ level: 5 }).run();
        },
      },
      {
        name: 'heading6',
        label: 'Heading 6',
        description: 'Extra small title text',
        iconName: 'lucide:heading-6',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).setHeading({ level: 6 }).run();
        },
      },
    ],
  },
  {
    name: 'embeds',
    title: 'Embeds',
    commands: [
                {
        name: 'embed-link',
        label: 'Embed Link',
        description: 'Embed a hyperlink',
        iconName: 'lucide:link',
        action: ({ editor, range }: CommandActionProps) => {
          editor.chain().focus().deleteRange(range).insertContent({
            type: 'embed',
          }).run();
        },
      },
      {
        name: 'YouTube',
        label: 'YouTube',
        description: 'Embed a YouTube video',
        iconName: 'si:youtube-fill',
        action: ({ editor, range }: CommandActionProps) => {
          editor
            .chain()
            .focus().deleteRange(range)
            .insertContent({
              type: 'youtubeUploader',
            })
            .run();
        },
      },
    ],
  },
]

export default GROUPS

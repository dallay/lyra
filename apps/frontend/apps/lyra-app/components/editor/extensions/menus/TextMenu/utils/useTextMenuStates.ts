import { computed } from "vue";
import { useTextMenuCommands } from "./useTextMenuCommands";
import type { Editor } from "@tiptap/vue-3";

export interface MenuItem {
  icon: string;
  label: string;
  isActive?: boolean | (() => boolean);
  onClick: () => void;
  shortcut?: string[];
  id?: string;
  disabled?: () => boolean;
}

export interface MenuGroups {
  group1: MenuItem[];
  group2: MenuItem[];
  headings: MenuItem[];
  lists: MenuItem[];
}

export function useTextMenuState(editor: Editor) {
  const commands = useTextMenuCommands(editor);

  const activeStates = computed(() => ({
    bold: editor.isActive("bold"),
    italic: editor.isActive("italic"),
    strike: editor.isActive("strike"),
    underline: editor.isActive("underline"),
    code: editor.isActive("code"),
    subscript: editor.isActive("subscript"),
    superscript: editor.isActive("superscript"),
    alignLeft: editor.isActive({ textAlign: "left" }),
    alignCenter: editor.isActive({ textAlign: "center" }),
    alignRight: editor.isActive({ textAlign: "right" }),
    alignJustify: editor.isActive({ textAlign: "justify" }),
    paragraph: editor.isActive("paragraph") &&
      !editor.isActive("orderedList") &&
      !editor.isActive("bulletList") &&
      !editor.isActive("taskList"),
    heading1: editor.isActive("heading", { level: 1 }),
    heading2: editor.isActive("heading", { level: 2 }),
    heading3: editor.isActive("heading", { level: 3 }),
    heading4: editor.isActive("heading", { level: 4 }),
    heading5: editor.isActive("heading", { level: 5 }),
    heading6: editor.isActive("heading", { level: 6 }),
    bulletList: editor.isActive("bulletList"),
    orderedList: editor.isActive("orderedList"),
    taskList: editor.isActive("taskList"),
  }));

  const menuGroups = computed<MenuGroups>(() => ({
    group1: [
      {
        icon: "lucide:bold",
        label: "Bold",
        isActive: activeStates.value.bold,
        onClick: commands.onBold,
        shortcut: ["Mod", "B"],
        id: "bold",
      },
      {
        icon: "lucide:italic",
        label: "Italic",
        isActive: activeStates.value.italic,
        onClick: commands.onItalic,
        shortcut: ["Mod", "I"],
        id: "italic",
      },
      {
        icon: "lucide:underline",
        label: "Underline",
        isActive: activeStates.value.underline,
        onClick: commands.onUnderline,
        shortcut: ["Mod", "U"],
        id: "underline",
      },
      {
        icon: "lucide:strikethrough",
        label: "Strikethrough",
        isActive: activeStates.value.strike,
        onClick: commands.onStrike,
        shortcut: ["Mod", "Shift", "S"],
        id: "strikethrough",
      },
      {
        icon: "lucide:code",
        label: "Code",
        isActive: activeStates.value.code,
        onClick: commands.onCode,
        shortcut: ["Mod", "E"],
        id: "code",
      },
      {
        icon: "lucide:subscript",
        label: "Subscript",
        isActive: activeStates.value.subscript,
        onClick: commands.onSubscript,
        shortcut: ["Mod", "."],
        id: "subscript",
      },
      {
        icon: "lucide:superscript",
        label: "Superscript",
        isActive: activeStates.value.superscript,
        onClick: commands.onSuperscript,
        shortcut: ["Mod", ","],
        id: "superscript",
      },
      {
        icon: "lucide:file-code",
        label: "Code block",
        onClick: commands.onCodeBlock,
        id: "codeBlock",
      },
    ],
    group2: [
      {
        icon: "lucide:align-left",
        label: "Align left",
        isActive: activeStates.value.alignLeft,
        onClick: commands.onAlignLeft,
        shortcut: ["Shift", "Mod", "L"],
        id: "alignLeft",
      },
      {
        icon: "lucide:align-center",
        label: "Align center",
        isActive: activeStates.value.alignCenter,
        onClick: commands.onAlignCenter,
        shortcut: ["Shift", "Mod", "E"],
        id: "alignCenter",
      },
      {
        icon: "lucide:align-right",
        label: "Align right",
        isActive: activeStates.value.alignRight,
        onClick: commands.onAlignRight,
        shortcut: ["Shift", "Mod", "R"],
        id: "alignRight",
      },
      {
        icon: "lucide:align-justify",
        label: "Justify",
        isActive: activeStates.value.alignJustify,
        onClick: commands.onAlignJustify,
        shortcut: ["Shift", "Mod", "J"],
        id: "alignJustify",
      },
    ],
    headings: [
      {
        icon: "lucide:pilcrow",
        label: "Paragraph",
        isActive: activeStates.value.paragraph,
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setParagraph()
            .run(),
        id: "paragraph",
        disabled: () => !editor.can().setParagraph(),
        shortcut: ["Mod", "Alt", "0"],
      },
      {
        icon: "lucide:heading-1",
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setHeading({ level: 1 })
            .run(),
        id: "heading1",
        disabled: () => !editor.can().setHeading({ level: 1 }),
        isActive: activeStates.value.heading1,
        label: "Heading 1",
        shortcut: ["Mod", "Alt", "1"],
      },
      {
        icon: "lucide:heading-2",
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setHeading({ level: 2 })
            .run(),
        id: "heading2",
        disabled: () => !editor.can().setHeading({ level: 2 }),
        isActive: activeStates.value.heading2,
        label: "Heading 2",
        shortcut: ["Mod", "Alt", "2"],
      },
      {
        icon: "lucide:heading-3",
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setHeading({ level: 3 })
            .run(),
        id: "heading3",
        disabled: () => !editor.can().setHeading({ level: 3 }),
        isActive: activeStates.value.heading3,
        label: "Heading 3",
        shortcut: ["Mod", "Alt", "3"],
      },
      {
        icon: "lucide:heading-4",
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setHeading({ level: 4 })
            .run(),
        id: "heading4",
        disabled: () => !editor.can().setHeading({ level: 4 }),
        isActive: activeStates.value.heading4,
        label: "Heading 4",
        shortcut: ["Mod", "Alt", "4"],
      },
      {
        icon: "lucide:heading-5",
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setHeading({ level: 5 })
            .run(),
        id: "heading5",
        disabled: () => !editor.can().setHeading({ level: 5 }),
        isActive: activeStates.value.heading5,
        label: "Heading 5",
        shortcut: ["Mod", "Alt", "5"],
      },
      {
        icon: "lucide:heading-6",
        onClick: () =>
          editor
            .chain()
            .focus()
            .lift("taskItem")
            .liftListItem("listItem")
            .setHeading({ level: 6 })
            .run(),
        id: "heading6",
        disabled: () => !editor.can().setHeading({ level: 6 }),
        isActive: activeStates.value.heading6,
        label: "Heading 6",
        shortcut: ["Mod", "Alt", "6"],
      }
    ],
    lists: [
      {
        icon: "lucide:list",
        onClick: () => editor.chain().focus().toggleBulletList().run(),
        id: "bulletList",
        disabled: () => !editor.can().toggleBulletList(),
        isActive: activeStates.value.bulletList,
        label: "Bullet list",
        shortcut: ["Mod", "Shift", "8"],
      },
      {
        icon: "lucide:list-ordered",
        onClick: () => editor.chain().focus().toggleOrderedList().run(),
        id: "orderedList",
        disabled: () => !editor.can().toggleOrderedList(),
        isActive: activeStates.value.orderedList,
        label: "Numbered list",
        shortcut: ["Mod", "Shift", "7"],
      },
      {
        icon: "lucide:list-todo",
        onClick: () => editor.chain().focus().toggleTaskList().run(),
        id: "todoList",
        disabled: () => !editor.can().toggleTaskList(),
        isActive: activeStates.value.taskList,
        label: "Todo list",
        shortcut: ["Mod", "Shift", "9"],
      },
    ]
  }));

  return { activeStates, menuGroups, commands };
}

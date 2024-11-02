// useTextMenuState.ts
import { computed } from "vue";
import { useTextMenuCommands } from "./useTextMenuCommands";
import type { Editor } from "@tiptap/vue-3";

export function useTextMenuState(editor: Editor) {
  // Comandos
  const commands = useTextMenuCommands(editor);

  // Estados activos
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
  }));

  // Grupos de menú
  const menuGroups = computed(() => ({
    group1: [
      {
        icon: "lucide:bold",
        label: "Bold",
        isActive: activeStates.value.bold,
        onClick: commands.onBold,
        shortcut: ["Mod", "B"],
      },
      {
        icon: "lucide:italic",
        label: "Italic",
        isActive: activeStates.value.italic,
        onClick: commands.onItalic,
        shortcut: ["Mod", "I"],
      },
      {
        icon: "lucide:underline",
        label: "Underline",
        isActive: activeStates.value.underline,
        onClick: commands.onUnderline,
        shortcut: ["Mod", "U"],
      },
      {
        icon: "lucide:strikethrough",
        label: "Strikethrough",
        isActive: activeStates.value.strike,
        onClick: commands.onStrike,
        shortcut: ["Mod", "Shift", "S"],
      },
      {
        icon: "lucide:code",
        label: "Code",
        isActive: activeStates.value.code,
        onClick: commands.onCode,
        shortcut: ["Mod", "E"],
      },
      {
        icon: "lucide:subscript",
        label: "Subscript",
        isActive: activeStates.value.subscript,
        onClick: commands.onSubscript,
        shortcut: ["Mod", "."],
      },
      {
        icon: "lucide:superscript",
        label: "Superscript",
        isActive: activeStates.value.superscript,
        onClick: commands.onSuperscript,
        shortcut: ["Mod", ","],
      },
      {
        icon: "lucide:file-code",
        label: "Code block",
        onClick: commands.onCodeBlock,
      },
    ],
    group2: [
      {
        icon: "lucide:align-left",
        label: "Align left",
        isActive: activeStates.value.alignLeft,
        onClick: commands.onAlignLeft,
        shortcut: ["Shift", "Mod", "L"],
      },
      {
        icon: "lucide:align-center",
        label: "Align center",
        isActive: activeStates.value.alignCenter,
        onClick: commands.onAlignCenter,
        shortcut: ["Shift", "Mod", "E"],
      },
      {
        icon: "lucide:align-right",
        label: "Align right",
        isActive: activeStates.value.alignRight,
        onClick: commands.onAlignRight,
        shortcut: ["Shift", "Mod", "R"],
      },
      {
        icon: "lucide:align-justify",
        label: "Justify",
        isActive: activeStates.value.alignJustify,
        onClick: commands.onAlignJustify,
        shortcut: ["Shift", "Mod", "J"],
      },
    ],
  }));

  return { activeStates, menuGroups, commands };
}

<script setup lang="ts">
import { computed, defineProps, reactive } from "vue";
import { BubbleMenu as BaseBubbleMenu } from "@tiptap/vue-3";
import { sticky, type Props } from "tippy.js";
import { Card } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import ContentTypePicker from "./components/ContentTypePicker.vue";
import FontFamilyPicker from "./components/FontFamilyPicker.vue";
import FontSizePicker from "./components/FontSizePicker.vue";
import EditLinkPopover from "./components/EditLinkPopover.vue";
import ColorPickerPopover from "./components/ColorPickerPopover.vue";
import MenuButtonGroup from "../MenuButtonGroup.vue";
import { useTextMenuContentTypes } from "./utils/useTextMenuContentTypes";
import { useTextMenuCommands } from "./utils/useTextMenuCommands";
import type { MenuProps } from "../types";
import type { EditorView } from "@tiptap/pm/view";
import { isCustomNodeSelected, isTextSelected } from "@/components/editor/lib/";

const props = defineProps<MenuProps>();
const pluginKey = computed(() => `textMenu-${crypto.randomUUID()}`);
const blockOptions = useTextMenuContentTypes(props.editor);
const commands = useTextMenuCommands(props.editor);

const activeStates = computed(() => {
  const editor = props.editor;
  return {
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
  };
});

const currentAttributes = computed(() => {
  const editor = props.editor;
  return {
    color: editor.getAttributes("textStyle")?.color,
    highlight: editor.getAttributes("highlight")?.color,
    font: editor.getAttributes("textStyle")?.fontFamily,
    size: editor.getAttributes("textStyle")?.fontSize,
  };
});

const stateMenus = reactive({
  buttons: [
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
      icon: "lucide:file-code",
      label: "Code block",
      onClick: commands.onCodeBlock,
    },
  ],
});

const stateMenus2 = reactive({
  buttons: [
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
});

const shouldShow = ({ view, from }: { view: EditorView; from: number }) => {
  if (!view || props.editor.view.dragging) {
    return false;
  }

  const domAtPos = view.domAtPos(from || 0).node;
  const nodeDOM = view.nodeDOM(from || 0);
  const node =
    nodeDOM instanceof HTMLElement
      ? nodeDOM
      : domAtPos instanceof HTMLElement
      ? domAtPos
      : null;

  if (node && isCustomNodeSelected(props.editor, node)) {
    return false;
  }

  return isTextSelected({ editor: props.editor });
};

const tippyOptions: Partial<Props> = {
  role: "menu",
  maxWidth: "calc(100vw - 16px)",
  popperOptions: {
    placement: "top-start",
    modifiers: [
      {
        name: "preventOverflow",
        options: { boundary: "viewport", padding: 8 },
      },
      {
        name: "flip",
        options: {
          fallbackPlacements: ["bottom-start", "top-end", "bottom-end"],
        },
      },
    ],
  },
  plugins: [sticky],
  sticky: "popper",
};
</script>

<template>
  <BaseBubbleMenu
    :editor="props.editor"
    :pluginKey="pluginKey"
    :shouldShow="shouldShow"
    :updateDelay="100"
    :tippy-options="tippyOptions"
  >
    <Card>
      <div class="flex items-center gap-1 w-full p-0">
        <ContentTypePicker :options="blockOptions" />
        <FontFamilyPicker :onChange="commands.onSetFont" :value="currentAttributes.font" />
        <FontSizePicker
          :onChange="commands.onSetFontSize"
          :value="currentAttributes.size"
        />
        <Separator orientation="vertical" class="w-px h-6 mx-2" />
        <MenuButtonGroup :buttons="stateMenus.buttons" />
        <EditLinkPopover :onSetLink="commands.onLink" />
        <ColorPickerPopover
          icon="Highlighter"
          label="Highlight"
          :is-active="!!currentAttributes.highlight"
          :color="currentAttributes.highlight"
          :on-change="commands.onChangeHighlight"
          :on-clear="commands.onClearHighlight"
        />
        <ColorPickerPopover
          icon="Palette"
          label="Text color"
          :is-active="!!currentAttributes.color"
          :color="currentAttributes.color"
          :on-change="commands.onChangeColor"
          :on-clear="commands.onClearColor"
        />
        <Separator orientation="vertical" class="w-px h-6 mx-2" />
        <MenuButtonGroup :buttons="stateMenus2.buttons" />
      </div>
    </Card>
  </BaseBubbleMenu>
</template>

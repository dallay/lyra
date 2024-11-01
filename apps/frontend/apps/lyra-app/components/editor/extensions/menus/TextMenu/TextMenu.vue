<template>
  <BaseBubbleMenu
    :editor="editor"
    :pluginKey="pluginKey"
    :shouldShow="states.shouldShow"
    :updateDelay="100"
    :tippy-options="{
      role: 'menu',
      maxWidth: 'calc(100vw - 16px)',
      popperOptions: {
        placement: 'top-start',
          modifiers: [
            {
              name: 'preventOverflow',
              options: {
                boundary: 'viewport',
                padding: 8,
              },
            },
            {
              name: 'flip',
              options: {
                fallbackPlacements: ['bottom-start', 'top-end', 'bottom-end'],
              },
            },
          ],
      },
          onCreate: (instance: Instance) => {
          tippyInstance = instance;
        },
        plugins: [sticky],
        sticky: 'popper',
     }"
  >
    <Card>
      <div class="flex items-center gap-1 w-full p-1">
        <ContentTypePicker :options="blockOptions" />
        <FontFamilyPicker
          :onChange="commands.onSetFont"
          :value="states.currentFont.value || ''"
        />
        <FontSizePicker
          :onChange="commands.onSetFontSize"
          :value="states.currentSize.value || ''"
        />
        <Separator orientation="vertical" class="w-px h-6 mx-2" />
        <MenuButtonGroup :buttons="menuButtons" />
        <EditLinkPopover :onSetLink="commands.onLink" />
      </div>
    </Card>
  </BaseBubbleMenu>
</template>

<script setup lang="ts">
import { ref, computed, defineProps } from "vue";
import { BubbleMenu as BaseBubbleMenu } from "@tiptap/vue-3";
import { sticky, type Instance } from "tippy.js";
import { Card } from "@/components/ui/card";
import { Icon } from "@/components/ui/icon";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import { Input } from "@/components/ui/input";
import type { MenuProps } from "../types";
import ContentTypePicker from "./components/ContentTypePicker.vue";
import FontFamilyPicker from "./components/FontFamilyPicker.vue";
import FontSizePicker from "./components/FontSizePicker.vue";
import EditLinkPopover from "./components/EditLinkPopover.vue";
import { useTextMenuContentTypes } from "./utils/useTextMenuContentTypes";
import { useTextMenuStates } from "./utils/useTextMenuStates";
import { useTextMenuCommands } from "./utils/useTextMenuCommands";
import MenuButtonGroup from "../MenuButtonGroup.vue";
import type { MenuButtonProps } from "../MenuButton.vue";

const props = defineProps<MenuProps>();
const tippyInstance = ref<Instance | null>(null);
const pluginKey = computed(() => `textMenu-${crypto.randomUUID()}`);
const blockOptions = useTextMenuContentTypes(props.editor);
const states = useTextMenuStates(props.editor);
const commands = useTextMenuCommands(props.editor);
const menuButtons: MenuButtonProps[] = [
  {
    icon: "lucide:bold",
    label: "Bold",
    isActive: states.isBold.value,
    onClick: commands.onBold,
    shortcut: ["Mod", "B"],
  },
  {
    icon: "lucide:italic",
    label: "Italic",
    isActive: states.isItalic.value,
    onClick: commands.onItalic,
    shortcut: ["Mod", "I"],
  },
  {
    icon: "lucide:underline",
    label: "Underline",
    isActive: states.isUnderline.value,
    onClick: commands.onUnderline,
    shortcut: ["Mod", "U"],
  },
  {
    icon: "lucide:strikethrough",
    label: "Strikethrough",
    isActive: states.isStrike.value,
    onClick: commands.onStrike,
    shortcut: ["Mod", "Shift", "S"],
  },
  {
    icon: "lucide:code",
    label: "Code",
    isActive: states.isCode.value,
    onClick: commands.onCode,
    shortcut: ["Mod", "E"],
  },
  {
    icon: "lucide:file-code",
    label: "Code block",
    onClick: commands.onCodeBlock
  },
];
</script>

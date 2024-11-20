<script setup lang="ts">
import {defineProps, ref, watch} from "vue";
import type {MenuProps} from "../types";
import {DragHandleVue} from "./DragHandleVue";
import {Icon} from "@/components/ui/icon";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {Button} from "@/components/ui/button";
import MenuButton from "../MenuButton.vue";
import useData from "~/components/editor/extensions/menus/ContentItemMenu/useData";
import useContentItemActions from "~/components/editor/extensions/menus/ContentItemMenu/useContentItemActions";

const props = defineProps<MenuProps>();
const menuOpen = ref(false);
const data = useData();
const actions = useContentItemActions(props.editor, data.currentNode.value, data.currentNodePos.value);

watch(menuOpen, (isOpen) => {
  if (isOpen) {
    // props.editor.chain().focus().lockDragHandle().run();
    props.editor.commands.setMeta('lockDragHandle', true);
  } else {
    // props.editor.chain().focus().unlockDragHandle().run();
    props.editor.commands.setMeta('lockDragHandle', false);
  }
});
</script>

<template>
  <div v-if="editor">
    <drag-handle-vue :editor="editor"
                     :tippy-options="{
                        offset: [-2, 16],
                        zIndex: 99,
                        duration: [100, 2000],
                        interactive: true,
                     }"
                     class="flex justify-center items-center w-full h-full">
      <MenuButton
        icon="lucide:plus"
        label="Add a new block"
        @click="actions.handleAdd()"
      />
      <Popover v-model:open="menuOpen">
        <PopoverTrigger asChild>
          <MenuButton icon="lucide:grip-vertical" label="Content Item Menu"/>
        </PopoverTrigger>
        <PopoverContent
          side="bottom"
          align="start"
          :sideOffset="8"
          class="flex flex-col justify-center items-start p-1 w-full"
        >
          <div class="p-2 flex flex-col">
            <Button
              variant="ghost"
              class="w-full flex justify-start items-start space-x-2"
              @click="actions.resetTextFormatting()"
            >
              <Icon name="RemoveFormatting"/>
              <span>Clear formatting</span>
            </Button>
            <Button
              variant="ghost"
              class="w-full flex justify-start items-start space-x-2"
              @click="actions.copyNodeToClipboard()"
            >
              <Icon name="Clipboard"/>
              <span>Copy to Clipboard</span>
            </Button>
            <Button
              variant="ghost"
              class="w-full flex justify-start items-start space-x-2 hover:text-red-500"
              @click="actions.deleteNode()"
            >
              <Icon name="Trash2"/>
              <span>Delete</span>
            </Button>
          </div>
        </PopoverContent>
      </Popover>
    </drag-handle-vue>
  </div>
</template>

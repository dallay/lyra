<script setup lang="ts">
import { ref, computed, watch, onMounted } from "vue";
import { BubbleMenu as BaseBubbleMenu, findParentNode } from "@tiptap/vue-3";
import { Card } from "@/components/ui/card";
import { Icon } from "@/components/ui/icon";
import { Button, buttonVariants } from "@/components/ui/button";
import { Switch } from "@/components/ui/switch";
import { Separator } from "@/components/ui/separator";
import { cn } from "~/lib/utils";
import type {
  MenuProps,
  ShouldShowProps,
} from "@/components/editor/extensions/v1/menus/types";
import {isColumnGripSelected} from "./utils";
import { TableMap } from "@tiptap/pm/tables";

const props = defineProps<MenuProps>();

const isHeaderColumnActive = computed((): boolean => {
  const { state } = props.editor;
  const { selection } = state;
  const table = findParentNode(node => node.type.name === 'table')(selection);

  if (table) {
    const map = TableMap.get(table.node);

     for (let rowIndex = 0; rowIndex < map.height; rowIndex++) {
      const cellPos = map.map[rowIndex * map.width];
      const cell = table.node.nodeAt(cellPos);

      if (cell?.type.name !== 'tableHeader') {
        return false;
      }
    }

    return true;
  }

  return false;
});

const isColumnHead = ref(isHeaderColumnActive.value);

watch(isHeaderColumnActive, (newValue) => {
  isColumnHead.value = newValue;
});

const shouldShowTableColumnMenu = ({
  editor,
  view,
  state,
  from,
}: ShouldShowProps): boolean => {
  if (!state || !from) {
    return false;
  }

  return isColumnGripSelected({ editor, view, state, from });
};

const onAddColumnBefore = () => {
  props.editor.chain().focus().addColumnBefore().run();
};

const onAddColumnAfter = () => {
  props.editor.chain().focus().addColumnAfter().run();
};

const onDeleteColumn = () => {
  props.editor.chain().focus().deleteColumn().run();
};

const onToggleColumnHead = () => {
  props.editor.chain().focus().toggleHeaderColumn().run();
  isColumnHead.value = !isColumnHead.value;
};

const isFirstColumn = computed(() => {
  const { state } = props.editor;
  const { selection } = state;
  const table = findParentNode(node => node.type.name === 'table')(selection);
  if (table) {
    const firstColumn = table.node.firstChild?.firstChild;
    const currentColumn = findParentNode(node => node.type.name === 'tableCell' || node.type.name === 'tableHeader')(selection);
    return currentColumn && currentColumn.node === firstColumn;
  }
  return false;
});

const shouldShowColumnHeadToggle = computed(() => {
  return isFirstColumn.value && props.editor.can().toggleHeaderCell();
});

const canAddColumnBefore = computed(() => {
  return !isFirstColumn.value || !isHeaderColumnActive.value;
});

onMounted(() => {
  isColumnHead.value = isHeaderColumnActive.value;
});
</script>

<template>
  <BaseBubbleMenu
    :shouldShow="shouldShowTableColumnMenu"
    :editor="editor"
    :updateDelay="0"
    pluginKey="tableColumnMenu"
    :tippy-options="{
      offset: [0, 15],
      popperOptions: {
        modifiers: [{ name: 'flip', enabled: false }],
      },
    }"
  >
    <Card>
      <div class="flex flex-col justify-center items-start p-1 w-full">
        <div
          v-if="shouldShowColumnHeadToggle"
          :class="
            cn(
              buttonVariants({
                variant: 'ghost',
                size: 'default',
              }).replace('hover:bg-accent hover:text-accent-foreground', ''),
              'w-full flex justify-center items-center space-x-2'
            )
          "
        >
          <Icon name="gravity-ui:layout-header-cells" />
          <span>Set as column head</span>
          <Switch :checked="isColumnHead" @update:checked="onToggleColumnHead" />
        </div>
        <Separator v-if="shouldShowColumnHeadToggle" class="my-1" />
        <Button
          v-if="canAddColumnBefore"
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
          @click="onAddColumnBefore"
        >
          <Icon name="ArrowLeftToLine" />
          <span>Add Column Before</span>
        </Button>
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
          @click="onAddColumnAfter"
        >
          <Icon name="ArrowRightToLine" />
          <span>Add Column After</span>
        </Button>
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
          @click="onDeleteColumn"
        >
          <Icon name="Trash" />
          <span>Delete Column</span>
        </Button>
      </div>
    </Card>
  </BaseBubbleMenu>
</template>

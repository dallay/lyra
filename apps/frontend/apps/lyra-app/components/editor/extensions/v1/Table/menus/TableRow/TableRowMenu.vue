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
import isRowGripSelected from "./utils";

const props = defineProps<MenuProps>();

const isHeaderRowActive = computed((): boolean => {
  const { state } = props.editor;
  const { selection } = state;
  const table = findParentNode(node => node.type.name === 'table')(selection);
  if (table) {
    const firstRow = table.node.firstChild;
    if (firstRow) {
      return firstRow.firstChild?.type.name === 'tableHeader';
    }
  }

  return false;
});

const isRowHead = ref(isHeaderRowActive.value);

watch(isHeaderRowActive, (newValue) => {
  isRowHead.value = newValue;
});

const shouldShowTableRowMenu = ({
  editor,
  view,
  state,
  from,
}: ShouldShowProps): boolean => {
  if (!state || !from) {
    return false;
  }

  return isRowGripSelected({ editor, view, state, from });
};

const onAddRowBefore = () => {
  props.editor.chain().focus().addRowBefore().run();
};

const onAddRowAfter = () => {
  props.editor.chain().focus().addRowAfter().run();
};

const onDeleteRow = () => {
  props.editor.chain().focus().deleteRow().run();
};

const onToggleRowHead = () => {
  props.editor.chain().focus().toggleHeaderRow().run();
  isRowHead.value = !isRowHead.value;
};

const isFirstRow = computed(() => {
  const { state } = props.editor;
  const { selection } = state;
  const table = findParentNode(node => node.type.name === 'table')(selection);
  if (table) {
    const firstRow = table.node.firstChild;
    const currentRow = findParentNode(node => node.type.name === 'tableRow')(selection);
    return currentRow && currentRow.node === firstRow;
  }
  return false;
});

const shouldShowRowHeadToggle = computed(() => {
  return isFirstRow.value && props.editor.can().toggleHeaderCell();
});

const canAddRowBefore = computed(() => {
  return !isFirstRow.value || !isHeaderRowActive.value;
});

onMounted(() => {
  isRowHead.value = isHeaderRowActive.value;
});
</script>

<template>
  <BaseBubbleMenu
    :shouldShow="shouldShowTableRowMenu"
    :editor="editor"
    :updateDelay="0"
    pluginKey="tableRowMenu"
    :tippy-options="{
      placement: 'left',
      offset: [0, 10],
      popperOptions: {
        modifiers: [{ name: 'flip', enabled: false }],
      },
    }"
  >
    <Card>
      <div class="flex flex-col justify-center items-start p-1 w-full">
        <div
          v-if="shouldShowRowHeadToggle"
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
          <span>Set as row head</span>
          <Switch :checked="isRowHead" @update:checked="onToggleRowHead" />
        </div>
        <Separator v-if="shouldShowRowHeadToggle" class="my-1" />
        <Button
          v-if="canAddRowBefore"
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
          @click="onAddRowBefore"
        >
          <Icon name="ArrowUpToLine" />
          <span>Add Row Before</span>
        </Button>
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
          @click="onAddRowAfter"
        >
          <Icon name="ArrowDownToLine" />
          <span>Add Row After</span>
        </Button>
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
          @click="onDeleteRow"
        >
          <Icon name="Trash" />
          <span>Delete Row</span>
        </Button>
      </div>
    </Card>
  </BaseBubbleMenu>
</template>

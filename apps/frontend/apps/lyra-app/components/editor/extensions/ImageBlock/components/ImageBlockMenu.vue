<template>
  <BaseBubbleMenu
    :editor="editor"
    :pluginKey="pluginKey"
    :shouldShow="shouldShow"
    :updateDelay="0"
    :tippy-options="{
      popperOptions: {
          modifiers: [{ name: 'flip', enabled: false }],
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
        <TooltipProvider>
          <Tooltip>
            <TooltipTrigger>
              <Button
                variant="ghost"
                class="flex"
                :active="isImageLeft"
                @click="onAlignImageLeft"
              >
                <Icon name="AlignHorizontalDistributeStart" />
                <span class="sr-only">Align Left</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Align Left</p>
            </TooltipContent>
          </Tooltip>

          <Tooltip>
            <TooltipTrigger>
              <Button
                variant="ghost"
                class="flex"
                :active="isImageCenter"
                @click="onAlignImageCenter"
              >
                <Icon name="AlignHorizontalDistributeCenter" />
                <span class="sr-only">Align Center</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Align Center</p>
            </TooltipContent>
          </Tooltip>

          <Tooltip>
            <TooltipTrigger>
              <Button
                variant="ghost"
                class="flex"
                :active="isImageRight"
                @click="onAlignImageRight"
              >
                <Icon name="AlignHorizontalDistributeEnd" />
                <span class="sr-only">Align Right</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Align Right</p>
            </TooltipContent>
          </Tooltip>
        </TooltipProvider>

        <div class="flex w-36">
          <Separator orientation="vertical" class="w-px h-6 mx-2"/>
          <ImageBlockWidth :onChange="onWidthChange" :value="width" />
        </div>
      </div>
    </Card>
  </BaseBubbleMenu>
</template>

<script setup lang="ts">
import { ref, computed, defineProps, watch } from "vue";
import { BubbleMenu as BaseBubbleMenu, useEditor } from "@tiptap/vue-3";
import { sticky, type Instance } from "tippy.js";
import { Card } from "@/components/ui/card";
import { Icon } from "@/components/ui/icon";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import ImageBlockWidth from "./ImageBlockWidth.vue";
import type { MenuProps } from "../../menus/types";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";

const props = defineProps<MenuProps>();
const tippyInstance = ref<Instance | null>(null);
const pluginKey = computed(() => `imageBlockMenu-${crypto.randomUUID()}`);

const shouldShow = () => {
  return props.editor.isActive("imageBlock");
};

const onAlignImageLeft = () => {
  props.editor
    .chain()
    .focus(undefined, { scrollIntoView: false })
    .setImageBlockAlign("left")
    .run();
};

const onAlignImageCenter = () => {
  props.editor
    .chain()
    .focus(undefined, { scrollIntoView: false })
    .setImageBlockAlign("center")
    .run();
};

const onAlignImageRight = () => {
  props.editor
    .chain()
    .focus(undefined, { scrollIntoView: false })
    .setImageBlockAlign("right")
    .run();
};

const onWidthChange = (value: number) => {
  props.editor
    .chain()
    .focus(undefined, { scrollIntoView: false })
    .setImageBlockWidth(value)
    .run();
};

const isImageLeft = computed(() =>
  props.editor.isActive("imageBlock", { align: "left" })
);
const isImageCenter = computed(() =>
  props.editor.isActive("imageBlock", { align: "center" })
);
const isImageRight = computed(() =>
  props.editor.isActive("imageBlock", { align: "right" })
);
const width = computed(() =>
  parseInt(props.editor.getAttributes("imageBlock")?.width || 0)
);
</script>

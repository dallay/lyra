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
                @click="onLayoutImageLeft"
              >
                <Icon name="lucide:layout-panel-left" />
                <span class="sr-only">Layout Image Left / Text Right</span>
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
                @click="onLayoutImageCenter"
              >
                <Icon name="lucide:layout-panel-top" />
                <span class="sr-only">Layout Image Top / Text Bottom</span>
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
                @click="onLayoutImageRight"
              >
                <Icon name="lucide:layout-panel-left" class="transform rotate-180" />
                <span class="sr-only">Layout Image Right / Text Left</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Align Right</p>
            </TooltipContent>
          </Tooltip>
        </TooltipProvider>
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
import type { MenuProps } from "../types";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";

const props = defineProps<MenuProps>();
const tippyInstance = ref<Instance | null>(null);
const pluginKey = computed(() => `linkMenu-${crypto.randomUUID()}`);

const shouldShow = () => {
  const isActive = props.editor.isActive("link") || props.editor.isActive("embedLink");
  return isActive;
};

const onLayoutImageLeft = () => {
  console.log('onLayoutImageLeft');
  props.editor.chain().focus().setEmbedLinkLayout('left').run();
};

const onLayoutImageCenter = () => {
  console.log('onLayoutImageCenter');
  props.editor.chain().focus().setEmbedLinkLayout('top').run();
};

const onLayoutImageRight = () => {
  console.log('onLayoutImageRight');
  props.editor.chain().focus().setEmbedLinkLayout('right').run();
};

const isImageLeft = computed(() =>
 props.editor.isActive("embedLink", { layout: "left" })
);
const isImageCenter = computed(() =>
 props.editor.isActive("embedLink", { layout: "top" })
);
const isImageRight = computed(() =>
 props.editor.isActive("embedLink", { layout: "right" })
);
</script>

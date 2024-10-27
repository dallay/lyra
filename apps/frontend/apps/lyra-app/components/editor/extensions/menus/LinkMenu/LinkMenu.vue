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
                :active="isLayoutImageLeft"
                @click="onLayoutImageLeft"
              >
                <Icon name="lucide:layout-panel-left" />
                <span class="sr-only">Layout Image Left / Text Right</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Layout Image Left / Text Right</p>
            </TooltipContent>
          </Tooltip>

          <Tooltip>
            <TooltipTrigger>
              <Button
                variant="ghost"
                class="flex"
                :active="isLayoutImageCenter"
                @click="onLayoutImageTop"
              >
                <Icon name="lucide:layout-panel-top" />
                <span class="sr-only">Layout Image Top / Text Bottom</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Layout Image Top / Text Bottom</p>
            </TooltipContent>
          </Tooltip>

          <Tooltip>
            <TooltipTrigger>
              <Button
                variant="ghost"
                class="flex"
                :active="isLayoutImageRight"
                @click="onLayoutImageRight"
              >
                <Icon name="lucide:layout-panel-left" class="transform rotate-180" />
                <span class="sr-only">Layout Image Right / Text Left</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Layout Image Right / Text Left</p>
            </TooltipContent>
          </Tooltip>

          <Tooltip>
            <TooltipTrigger>
              <Button
                variant="ghost"
                class="flex"
                :active="isLayoutOnlyText"
                @click="onLayoutOnlyText"
              >
                <Icon name="lucide:text" />
                <span class="sr-only">Layout Only Text</span>
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Layout Only Text</p>
            </TooltipContent>
          </Tooltip>
        </TooltipProvider>
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

const shouldShow = () => props.editor.isActive("link") || props.editor.isActive("embedLink");

const onLayoutImageLeft = () =>  props.editor.chain().focus().setEmbedLinkLayout('left').run();

const onLayoutImageTop = () =>  props.editor.chain().focus().setEmbedLinkLayout('top').run();

const onLayoutImageRight = () => props.editor.chain().focus().setEmbedLinkLayout('right').run();

const onLayoutOnlyText = () => props.editor.chain().focus().setEmbedLinkLayout('text').run();

const isLayoutImageLeft = computed(() =>
 props.editor.isActive("embedLink", { layout: "left" })
);
const isLayoutImageCenter = computed(() =>
 props.editor.isActive("embedLink", { layout: "top" })
);
const isLayoutImageRight = computed(() =>
 props.editor.isActive("embedLink", { layout: "right" })
);
const isLayoutOnlyText = computed(() =>
 props.editor.isActive("embedLink", { layout: "text" })
);
</script>

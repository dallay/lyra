<template>
  <BubbleMenu
    :editor="editor"
    :pluginKey="pluginKey"
    :shouldShow="shouldShow"
    :tippyOptions="{
      offset: [0, 8],
      popperOptions: { modifiers: [{ name: 'flip', enabled: false }] },
      getReferenceClientRect,
      appendTo: appendTo?.value,
      plugins: [sticky],
      sticky: 'popper',
    }"
  >
    <!-- <Toolbar.Wrapper :shouldShowContent="shouldShow()" ref="menuRef">
      <Toolbar.Button tooltip="Align image left" :active="isImageLeft" @click="onAlignImageLeft">
        <Icon name="AlignHorizontalDistributeStart" />
      </Toolbar.Button>
      <Toolbar.Button tooltip="Align image center" :active="isImageCenter" @click="onAlignImageCenter">
        <Icon name="AlignHorizontalDistributeCenter" />
      </Toolbar.Button>
      <Toolbar.Button tooltip="Align image right" :active="isImageRight" @click="onAlignImageRight">
        <Icon name="AlignHorizontalDistributeEnd" />
      </Toolbar.Button>
      <Toolbar.Divider />
      <ImageBlockWidth :onChange="onWidthChange" :value="width" />
    </Toolbar.Wrapper> -->
    <p>TODO: Implement menu</p>
  </BubbleMenu>
</template>

<script setup lang="ts">
import { ref, computed, defineProps, watch } from 'vue';
import { BubbleMenu } from '@tiptap/vue-3';
import { sticky, type Instance } from 'tippy.js';

import ImageBlockWidth from './ImageBlockWidth.vue';
import type { MenuProps } from '../../menus/types';
import { getRenderContainer } from '@/components/editor/lib/getRenderContainer';

const props = defineProps<MenuProps>();

const menuRef = ref<HTMLDivElement | null>(null);
const tippyInstance = ref<Instance | null>(null);
const pluginKey = computed(() => `imageBlockMenu-${crypto.randomUUID()}`);

const getReferenceClientRect = () => {
  const renderContainer = getRenderContainer(props.editor, 'node-imageBlock');
  const rect = renderContainer?.getBoundingClientRect() || new DOMRect(-1000, -1000, 0, 0);
  return rect;
};

const shouldShow = () => {
  return props.editor.isActive('imageBlock');
};

const onAlignImageLeft = () => {
  props.editor.chain().focus(undefined, { scrollIntoView: false }).setImageBlockAlign('left').run();
};

const onAlignImageCenter = () => {
  props.editor.chain().focus(undefined, { scrollIntoView: false }).setImageBlockAlign('center').run();
};

const onAlignImageRight = () => {
  props.editor.chain().focus(undefined, { scrollIntoView: false }).setImageBlockAlign('right').run();
};

const onWidthChange = (value: number) => {
  props.editor.chain().focus(undefined, { scrollIntoView: false }).setImageBlockWidth(value).run();
};

const isImageLeft = ref(false);
const isImageCenter = ref(false);
const isImageRight = ref(false);
const width = ref(0);

watch(
  () => props.editor,
  (editor) => {
    const isActive = (type: string, options: Record<string, any>) => {
      return editor.isActive(type, options);
    };
    isImageLeft.value = isActive('imageBlock', { align: 'left' });
    isImageCenter.value = isActive('imageBlock', { align: 'center' });
    isImageRight.value = isActive('imageBlock', { align: 'right' });
    width.value = parseInt(editor.getAttributes('imageBlock')?.width || 0);
  },
  { immediate: true }
);
</script>

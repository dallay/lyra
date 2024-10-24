<script setup lang="ts">
import { ref, watch } from 'vue'
import { BubbleMenu, useEditor } from '@tiptap/vue-3'
import { sticky } from 'tippy.js'
import { ColumnLayout } from '../Columns'
import { getRenderContainer } from '@/components/editor/lib/getRenderContainer'
import type { MenuProps } from '../../menus/types'

const props = defineProps<Pick<MenuProps, 'editor' | 'appendTo'>>()

// Reactive states para columnas
const isColumnLeft = ref(false)
const isColumnRight = ref(false)
const isColumnTwo = ref(false)
const columnId = computed(() => `columnsMenu-${crypto.randomUUID()}`)

// Estado del editor para detectar los layouts de las columnas
watch(
  () => props.editor,
  (editor) => {
    const isActive = (type: string, options: { layout: ColumnLayout }) => {
      return editor.isActive(type, options)
    }
    isColumnLeft.value = isActive('columns', { layout: ColumnLayout.SidebarLeft })
    isColumnRight.value = isActive('columns', { layout: ColumnLayout.SidebarRight })
    isColumnTwo.value = isActive('columns', { layout: ColumnLayout.TwoColumn })
  },
  { immediate: true }
)

// Funciones para ajustar las columnas
const onColumnLeft = () => {
  props.editor.chain().focus().setLayout(ColumnLayout.SidebarLeft).run()
}

const onColumnRight = () => {
  props.editor.chain().focus().setLayout(ColumnLayout.SidebarRight).run()
}

const onColumnTwo = () => {
  props.editor.chain().focus().setLayout(ColumnLayout.TwoColumn).run()
}

const shouldShow = () => {
  return props.editor.isActive('columns')
}

const getReferenceClientRect = () => {
  const renderContainer = getRenderContainer(props.editor, 'columns')
  const rect = renderContainer?.getBoundingClientRect() || new DOMRect(-1000, -1000, 0, 0)
  return rect
}
</script>

<template>
  <BubbleMenu
    :editor="editor"
    :pluginKey="columnId"
    :shouldShow="shouldShow"
    :tippyOptions="{
      offset: [0, 8],
      popperOptions: { modifiers: [{ name: 'flip', enabled: false }] },
      getReferenceClientRect,
      appendTo: appendTo?.current,
      plugins: [sticky],
      sticky: 'popper',
    }"
  >
    <p>TODO: Implement</p>
  </BubbleMenu>
</template>

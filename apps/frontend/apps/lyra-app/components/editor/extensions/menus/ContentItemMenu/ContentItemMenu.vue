<template>
  <DragHandle
    pluginKey="ContentItemMenu"
    :editor="editor"
    @nodeChange="data.handleNodeChange"
    :tippyOptions="{ offset: [-2, 16], zIndex: 99 }"
  >
    <Card>
      <div class="flex flex-col justify-center items-start p-1 w-full">
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
        >
          <Icon name="ArrowLeftToLine" />
          <span>Add Column Before</span>
        </Button>
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
        >
          <Icon name="ArrowRightToLine" />
          <span>Add Column After</span>
        </Button>
        <Button
          variant="ghost"
          class="w-full flex justify-start items-start space-x-2"
        >
          <Icon name="Trash" />
          <span>Delete Column</span>
        </Button>
      </div>
    </Card>
  </DragHandle>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { Icon } from '@/components/ui/icon'
import DragHandle from '@tiptap-pro/extension-drag-handle'
import useContentItemActions from './utils/useContentItemActions'
import {useData} from './utils/useData'
import { Editor } from '@tiptap/vue-3'
import {cn} from "~/lib/utils";
import {Button, buttonVariants} from "~/components/ui/button";
import {Switch} from "~/components/ui/switch";
import {Separator} from "~/components/ui/separator";
import {Card} from "~/components/ui/card";

const props = defineProps({
  editor: Editor
})

const menuOpen = ref(false)
const data = useData()
const actions = props.editor ? useContentItemActions(props.editor, data.currentNode.value, data.currentNodePos.value) : null

watch(menuOpen, (isOpen) => {
  if (props.editor) {
    props.editor.commands.setMeta('lockDragHandle', isOpen)
  }
})
</script>

<style scoped>
/* Puedes agregar aquí tus estilos adicionales si los necesitas */
</style>

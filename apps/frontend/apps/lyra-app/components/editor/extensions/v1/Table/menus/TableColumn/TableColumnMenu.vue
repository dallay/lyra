<script setup lang="ts">
import { BubbleMenu as BaseBubbleMenu } from '@tiptap/vue-3'
import { Icon } from '@/components/ui/icon'
import type { MenuProps, ShouldShowProps } from '@/components/editor/extensions/v1/menus/types';

const props = defineProps<MenuProps>()

const shouldShowTableColumnMenu = ({ editor, state }: ShouldShowProps): boolean => {return false}
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
        modifiers: [{name: 'flip', enabled: false}]
      }
    }"
    >
      <p>This should be implemented soon</p>
    </BaseBubbleMenu>
</template>

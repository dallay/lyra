<script setup lang="ts">
import { NodeViewWrapper, Editor } from '@tiptap/vue-3'
import type { TableOfContentsStorage } from '@tiptap-pro/extension-table-of-contents'
import { ref, computed, defineProps, onMounted, watchEffect } from 'vue'
import { cn } from '@/lib/utils'

export type TableOfContentsProps = {
  editor: Editor
  onItemClick?: () => void
}

const props = defineProps<TableOfContentsProps>()

const content = ref((props.editor.storage.tableOfContents as TableOfContentsStorage).content)

onMounted(() => {
  watchEffect(() => {
    content.value = (props.editor.storage.tableOfContents as TableOfContentsStorage).content
  })
})
</script>

<template>
 <NodeViewWrapper>
  <div class="mb-2 text-xs font-semibold uppercase text-neutral-500 dark:text-neutral-400">
      Table of contents
    </div>
    <div v-if="content.length > 0" class="flex flex-col gap-1">
      <a
        v-for="item in content"
        :key="item.id"
        :href="`#${item.id}`"
        :style="{ marginLeft: `${1 * item.level - 1}rem` }"
        @click="onItemClick"
        :class="cn('block font-medium text-neutral-500 dark:text-neutral-300 p-1 rounded bg-opacity-10 text-sm hover:text-neutral-800 transition-all hover:bg-black hover:bg-opacity-5 truncate w-full',
          item.isActive && 'text-neutral-800 bg-neutral-100 dark:text-neutral-100 dark:bg-neutral-900',
        )"
      >
        {{ item.itemIndex }}. {{ item.textContent }}
      </a>
    </div>
    <div v-else class="text-sm text-neutral-500">
      Start adding headlines to your document …
    </div>
 </NodeViewWrapper>
</template>

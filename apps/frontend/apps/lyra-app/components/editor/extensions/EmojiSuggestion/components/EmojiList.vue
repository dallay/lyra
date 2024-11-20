<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import { Card } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import type { SuggestionKeyDownProps } from '@tiptap/suggestion'
import type { EmojiListProps } from '../types'

const props = defineProps<EmojiListProps>()

const selectedIndex = ref(0)

const selectItem = (index: number) => {
  const item = props.items[index]
  if (item) {
    props.command({ name: item.name })
  }
}

watch(
  () => props.items,
  () => {
    selectedIndex.value = 0
  },
)

const scrollIntoView = (index: number) => {
  const item = props.items[index]
  if (item) {
    const node = document.querySelector(`[data-emoji-name="${item.name}"]`)
    if (node) {
      node.scrollIntoView({ block: 'nearest' })
    }
  }
}

const upHandler = () => {
  selectedIndex.value = (selectedIndex.value + props.items.length - 1) % props.items.length
  scrollIntoView(selectedIndex.value)
}

const downHandler = () => {
  selectedIndex.value = (selectedIndex.value + 1) % props.items.length
  scrollIntoView(selectedIndex.value)
}

const enterHandler = () => {
  selectItem(selectedIndex.value)
}

const onKeyDown = (evt: SuggestionKeyDownProps) => {
  const { key } = evt.event
  if (key === 'ArrowUp') {
    upHandler()
    return true
  }
  if (key === 'ArrowDown') {
    downHandler()
    return true
  }
  if (key === 'Enter') {
    enterHandler()
    return true
  }
  return false
}

defineExpose({ onKeyDown });
</script>

<template>
  <Card class="overflow-y-auto max-w-[18rem] max-h-[18rem]">
    <Button
      v-for="(item, index) in items"
      :key="item.name"
      :active="index === selectedIndex"
      variant="ghost"
      class="justify-start w-full"
      buttonSize="small"
      @click="selectItem(index)"
      :data-emoji-name="item.name"
    >
      <img v-if="item.fallbackImage" :src="item.fallbackImage" class="w-5 h-5" alt="emoji" />
      <span v-else>{{ item.emoji }}</span>
      <span class="truncate text-ellipsis">:{{ item.name }}:</span>
    </Button>
  </Card>
</template>

<style scoped>
/* Add additional styles if necessary */
</style>

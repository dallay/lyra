<script setup lang="ts">
import { useColorMode } from '#imports'
import { Button } from '@/components/ui/button'
const colorMode = useColorMode()

const toggleDark = () => {
  colorMode.preference = colorMode.value === 'dark' ? 'light' : 'dark'
}
</script>

<template>
  <Button
    variant="ghost"
    size="icon"
    @click="toggleDark"
    class="flex items-center space-x-1"
  >
    <Icon
      v-if="colorMode.value === 'light'" name="ph:moon" color="black"
      class="h-4 w-4"/>
    <Icon v-else name="ph:sun" color="black" class="h-4 w-4"/>
  </Button>
</template>

<style scoped lang="scss">

</style>

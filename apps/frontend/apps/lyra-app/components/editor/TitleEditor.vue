<template>
  <div class="space-y-2 mb-4">
    <Input
      class="text-4xl font-bold border-none shadow-none focus-visible:ring-0"
      placeholder="New Post"
    />
    <Input
      class="text-xl text-muted-foreground border-none shadow-none focus-visible:ring-0"
      placeholder="Add a subtitle"
    />
  </div>
</template>

<script setup lang="ts">
import { Input } from '@/components/ui/input'
</script>

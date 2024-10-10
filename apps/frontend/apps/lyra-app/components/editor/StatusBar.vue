<template>
  <div class="flex justify-between items-center mb-4">
    <div class="flex space-x-2">
      <Badge variant="secondary">Draft</Badge>
      <Badge variant="outline" class="bg-green-50 text-green-700">Synced</Badge>
    </div>
    <div class="flex space-x-2">
      <UserAvatar />
      <Button variant="ghost" size="icon"><Undo class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Redo class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Search class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><MessageSquare class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Clock class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Printer class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Zap class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Info class="h-4 w-4" /></Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Badge } from '@/components/ui/badge'
import UserAvatar from '@/components/nav/UserAvatar.vue'
import { Button } from '@/components/ui/button'
import { Undo, Redo, Search, MessageSquare, Clock, Printer, Zap, Info } from 'lucide-vue-next'
</script>

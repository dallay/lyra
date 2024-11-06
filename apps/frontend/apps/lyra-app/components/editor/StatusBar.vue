<template>
  <div class="flex justify-between items-center mb-4">
    <div class="flex space-x-2">
      <Badge variant="outline" class="flex items-center space-x-2">
        <span class="text-muted-foreground">Draft</span>
        <Separator orientation="vertical" class="mx-2" />
        <span class="text-muted-foreground">Synced</span>
      </Badge>
    </div>
    <div class="flex justify-center items-center space-x-2">
      <UserAvatar class="size-6"/>
      <Button variant="ghost" size="icon"><Undo class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Redo class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Search class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><MessageSquare class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Clock class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Printer class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Zap class="h-4 w-4" /></Button>
      <Button variant="ghost" size="icon"><Info class="h-4 w-4" /></Button>
      <ThemeSwitcher />
    </div>
  </div>
</template>

<script setup lang="ts">
import { Badge } from '@/components/ui/badge';
import { Separator } from '@/components/ui/separator';
import UserAvatar from '@/components/nav/UserAvatar.vue';
import { Button } from '@/components/ui/button';
import { Undo, Redo, Search, MessageSquare, Clock, Printer, Zap, Info } from 'lucide-vue-next';
import ThemeSwitcher from "~/components/ThemeSwitcher.vue";
</script>

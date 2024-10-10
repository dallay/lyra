<template>
  <div class="flex space-x-2 mb-4">
    <Button variant="outline" class="text-muted-foreground">
      <Image class="h-4 w-4 mr-2" />
      Add thumbnail
    </Button>
    <Button variant="outline" class="text-muted-foreground">
      <Tag class="h-4 w-4 mr-2" />
      Add content tags
    </Button>
  </div>
</template>

<script setup lang="ts">
import { Button } from '@/components/ui/button';
import { Image, Tag } from 'lucide-vue-next';
</script>

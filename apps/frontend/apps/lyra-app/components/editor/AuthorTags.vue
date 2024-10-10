<template>
  <div class="flex items-center space-x-2 mb-4">
    <Badge class="bg-purple-100 text-purple-800">
      Yuniel Acosta Pérez
      <Button variant="ghost" size="icon" class="h-4 w-4 ml-2">
        <Plus class="h-3 w-3" />
      </Button>
    </Badge>
    <Button variant="ghost" size="sm" class="text-muted-foreground">
      <Plus class="h-4 w-4 mr-2" />
      Add author
    </Button>
  </div>
</template>

<script setup lang="ts">
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Plus } from 'lucide-vue-next';
</script>

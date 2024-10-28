<template>
  <div class="mx-2 flex items-center space-x-2 mb-4">
    <Badge class="rounded-full bg-purple-100 text-purple-800">
      {{ user?.name }}
      <Button variant="ghost" size="icon" class="h-4 w-4 ml-2">
        <X class="size-3" />
      </Button>
    </Badge>
    <Button variant="ghost" size="sm" class="text-muted-foreground" @click="addAuthor">
      <Plus class="h-4 w-4 mr-2" />
      Add author
    </Button>
  </div>
</template>

<script setup lang="ts">
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { X, Plus } from 'lucide-vue-next';
import { useAuthStore } from '~/store/auth.store';
import type { IUser } from '@/domain/user';
const { getUser } = useAuthStore();

import { ref, onMounted } from 'vue';

const user = ref<IUser | null>(null);

onMounted(async () => {
  user.value = await getUser();
});

const addAuthor = () => {
  alert('This feature will be implemented soon.');
};
</script>

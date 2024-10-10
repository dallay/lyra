<script setup lang="ts">
import { ref } from 'vue';
import { useAuthStore } from '~/store/auth.store';
import type { IUser } from '@/domain/user';
import { useUserAvatar } from '@/composables/useUserAvatar';
const { getUser } = useAuthStore();
const user = ref<IUser | null>(await getUser());
const { userAvatar } = useUserAvatar(user.value);
</script>

<template>
  <Avatar class="h-8 w-8">
    <AvatarImage :src="userAvatar" :alt="`@${user?.username}`" />
    <AvatarFallback>{{ initials(user?.name ?? "Y A P") }}</AvatarFallback>
  </Avatar>
</template>

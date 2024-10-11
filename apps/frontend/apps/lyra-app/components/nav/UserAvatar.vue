<script setup lang="ts">
import { ref, type HTMLAttributes } from 'vue';
import { cn } from '~/lib/utils';
import { Avatar, AvatarFallback, AvatarImage, type AvatarVariants, avatarVariant } from '@/components/ui/avatar';
import { useAuthStore } from '~/store/auth.store';
import type { IUser } from '@/domain/user';
import { useUserAvatar } from '@/composables/useUserAvatar';
const { getUser } = useAuthStore();
const user = ref<IUser | null>(await getUser());
const { userAvatar } = useUserAvatar(user.value);

interface AvatarProps {
  class?: HTMLAttributes['class']
  size?: AvatarVariants['size']
  shape?: AvatarVariants['shape']
}
const props = withDefaults(defineProps<AvatarProps>(), {
  size: 'xs',
  shape: 'circle',
})
</script>

<template>
  <Avatar :class="cn(avatarVariant({ size, shape }), props.class)">
    <AvatarImage :src="userAvatar" :alt="`@${user?.username}`" />
    <AvatarFallback>{{ initials(user?.name ?? "Y A P") }}</AvatarFallback>
  </Avatar>
</template>

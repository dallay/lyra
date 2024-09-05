<script setup lang="ts">
import {useRouter, onMounted, ref, randomNumber, avatar, navigateTo, watchEffect} from '#imports';
import { storeToRefs } from 'pinia';
import { useAuthStore } from '~/store/auth.store';
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
} from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import type {IUser} from "@/domain/user";

const router = useRouter();
const { isAuthenticated } = storeToRefs(useAuthStore());
const { getUser, logUserOut } = useAuthStore();
const user = ref<IUser|null>(await getUser());
const defaultAvatar = `/avatars/0${randomNumber(1,5)}.png`;
const userAvatar = user?.value?.email? avatar(user?.value.email) : defaultAvatar;

const logout = async () => {
  await logUserOut();
  await router.push('/login');
};

onMounted(() => {
  if (!isAuthenticated.value || !user.value) {
    router.push('/login');
  }
});
watchEffect(() => {
  if (!user.value) {
    navigateTo('/login')
  }
})
</script>

<template>
  <DropdownMenu>
    <DropdownMenuTrigger as-child>
      <Button variant="ghost" class="relative h-8 w-8 rounded-full">
        <Avatar class="h-8 w-8">
          <AvatarImage :src="userAvatar" alt="@shadcn" />
          <AvatarFallback>{{ initials(user?.name ?? 'Y A P') }}</AvatarFallback>
        </Avatar>
      </Button>
    </DropdownMenuTrigger>
    <DropdownMenuContent class="w-56" align="end">
      <DropdownMenuLabel class="font-normal flex">
        <div class="flex flex-col space-y-1">
          <p class="text-sm font-medium leading-none">
            {{ user?.username }}
          </p>
          <p class="text-xs leading-none text-muted-foreground">
            {{ user?.email }}
          </p>
        </div>
      </DropdownMenuLabel>
      <DropdownMenuSeparator />
      <DropdownMenuGroup>
        <DropdownMenuItem>
          Profile
          <DropdownMenuShortcut>⇧⌘P</DropdownMenuShortcut>
        </DropdownMenuItem>
        <DropdownMenuItem>
          Billing
          <DropdownMenuShortcut>⌘B</DropdownMenuShortcut>
        </DropdownMenuItem>
        <DropdownMenuItem>
          Settings
          <DropdownMenuShortcut>⌘S</DropdownMenuShortcut>
        </DropdownMenuItem>
        <DropdownMenuItem>New Team</DropdownMenuItem>
      </DropdownMenuGroup>
      <DropdownMenuSeparator />
      <DropdownMenuItem @click="logout">
        Log out
        <DropdownMenuShortcut>⇧⌘Q</DropdownMenuShortcut>
      </DropdownMenuItem>
    </DropdownMenuContent>
  </DropdownMenu>
</template>

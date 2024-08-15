<template>
  <slot />
</template>
<script lang="ts" setup>
import { useRouter, onMounted } from '#imports';
import { storeToRefs } from 'pinia';
import { useAuthStore } from '~/store/auth.store';

const router = useRouter();

const { isAuthenticated } = storeToRefs(useAuthStore());

onMounted(() => {
  if (!isAuthenticated.value) {
    router.push('/login');
  }
});
</script>

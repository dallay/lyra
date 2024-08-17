<script setup lang="ts">
import AppDashboard from "~/components/AppDashboard.vue";
import {navigateTo, onMounted} from "#imports";
import {storeToRefs} from "pinia";
import {useAuthStore} from "~/store/auth.store";
const { isAuthenticated } = storeToRefs(useAuthStore());

onMounted(() => {
  if (!isAuthenticated.value) {
    navigateTo('/login');
  }
});
</script>

<template>
  <AppDashboard />
</template>

<style scoped lang="scss">

</style>

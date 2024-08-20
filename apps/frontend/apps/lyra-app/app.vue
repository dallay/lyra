<script setup lang="ts">
// import 'reflect-metadata';
import { onMounted } from 'vue';
import Toaster from '@/components/ui/toast/Toaster.vue'
// import { useNuxtApp } from '#app';
// import { Container } from 'inversify';
// import {
//   authModule,
//   HTTP_AUTHENTICATION_SERVICE,
//   HttpAuthenticationService
// } from '@lyra/domain';

// const dependenciesContainer = new Container();
// const setupApplicationDependencies = async (): Promise<void> => {
//    dependenciesContainer.load(authModule);
// };
// await setupApplicationDependencies();
//
// const authController = dependenciesContainer.get<HttpAuthenticationService>(HTTP_AUTHENTICATION_SERVICE);
// const { $authController } = useNuxtApp();
// onMounted(async () => {
//   const accessToken = await $authController.getAccessToken();
//   console.log("🟢 Access Token: ", accessToken);
//   // // if the token is not present, redirect to the login page
//   // if (!accessToken) {
//   //   // route.
//   // }
// });
</script>
<template>
  <Toaster />
  <NuxtPage />
</template>

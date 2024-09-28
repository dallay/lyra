<script setup lang="ts">
import { definePageMeta } from '#imports';
definePageMeta({
  layout: 'simple',
});
import UserAuthForm from '@/components/UserAuthForm.vue';

const onError = async (event: Event) => {
  const target = event.target as HTMLImageElement;
  target.src = '/placeholder.svg';
};
</script>

<template>
  <div class="w-full lg:grid lg:min-h-screen lg:grid-cols-2 xl:min-h-screen">
    <div class="absolute">
    </div>
    <div class="flex items-center justify-center py-12">
      <div class="mx-4 md:mx-auto flex w-full flex-col justify-center space-y-6 sm:w-[350px]">
        <div class="flex flex-col space-y-2 text-center">
          <h1 class="text-2xl font-semibold tracking-tight">
            Create an account
          </h1>
          <p class="text-sm text-muted-foreground">
            Enter your email below to create your account
          </p>
        </div>
        <UserAuthForm/>
        <p class="px-8 text-center text-sm text-muted-foreground">
          By clicking continue, you agree to our
          <a
            href="/terms"
            class="underline underline-offset-4 hover:text-primary"
          >
            Terms of Service
          </a>
          and
          <a
            href="/privacy"
            class="underline underline-offset-4 hover:text-primary"
          >
            Privacy Policy
          </a>
          .
        </p>
      </div>
    </div>
    <div class="hidden bg-muted lg:block">
      <NuxtImg
        src="/login.jpg"
        alt="Image"
        width="1920"
        height="1080"
        class="h-full w-full object-cover dark:brightness-[0.2] dark:grayscale"
        @error="onError"
      />
    </div>
  </div>
</template>

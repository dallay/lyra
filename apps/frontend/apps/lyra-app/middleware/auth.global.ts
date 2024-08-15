import { storeToRefs } from 'pinia';
import { useAuthStore } from '~/store/auth.store';
import { defineNuxtRouteMiddleware, navigateTo, abortNavigation } from '#imports';

const publicRoutePatterns = [
  /^\/forms\/[a-zA-Z0-9-]+$/
];

export default defineNuxtRouteMiddleware((to) => {
  const { accessToken } = storeToRefs(useAuthStore());

  // Check if the route matches any of the public route patterns
  const isPublicRoute = publicRoutePatterns.some((pattern) => pattern.test(to.path));

  // Skip authentication logic for public routes
  if (isPublicRoute) {
    return;
  }

  // If token exists and url is /login redirect to homepage
  if (accessToken.value && to?.name === 'login') {
    return navigateTo('/');
  }

  // If token doesn't exist and the route is not login, redirect to login
  if (!accessToken.value && to?.name !== 'login') {
    abortNavigation();
    return navigateTo('/login');
  }
});

import { storeToRefs } from 'pinia';
import { useAuthStore } from '~/store/auth.store';
import { defineNuxtRouteMiddleware, navigateTo, useCookie } from '#imports';

const publicRoutePatterns = [
  /^\/forms\/[a-zA-Z0-9-]+$/
];

export default defineNuxtRouteMiddleware((to, from) => {
  const { accessToken } = storeToRefs(useAuthStore());

  const redirectCookie = useCookie('redirectPath');

  // Check if the route matches any of the public route patterns
  const isPublicRoute = publicRoutePatterns.some((pattern) => pattern.test(to.path));

  // Skip authentication logic for public routes
  if (isPublicRoute) {
    return;
  }

  // If token exists and url is /login redirect to the previous route or homepage
  if (accessToken.value && to?.name === 'login') {
    const redirectPath = redirectCookie.value || '/';
    redirectCookie.value = null; // Clear the cookie
    return navigateTo(redirectPath);
  }

  // If token doesn't exist and the route is not login, redirect to login
  if (!accessToken.value && to?.name !== 'login') {
    // Save the original route before redirecting
    redirectCookie.value = to.fullPath;
    return navigateTo('/login');
  }
});

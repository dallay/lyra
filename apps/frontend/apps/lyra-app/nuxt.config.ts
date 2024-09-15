import { defineNuxtConfig } from 'nuxt/config'
const { API_URL, BACKEND_URL, APP_CLIENT_URL } = process.env;
const defaultServer = 'http://localhost:8080';
const defaultClient = 'https://localhost:4000';
const backendUrl = BACKEND_URL || defaultServer;
const apiUrl = API_URL || defaultServer;
const appClientUrl = APP_CLIENT_URL || defaultClient;


// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  devtools: { enabled: true },
  modules: [
    '@nuxtjs/tailwindcss',
    'shadcn-nuxt',
    '@nuxt/icon',
    '@nuxtjs/color-mode',
    [
      '@pinia/nuxt',
      {
        autoImports: [
          'defineStore',
        ],
      },
    ],
    "@nuxt/image",
    "@nuxt/scripts",
    '@nuxt/test-utils/module'
  ],
  shadcn: {
    prefix: '',
    componentDir: './components/ui',
  },
  routeRules: {
    '/': { prerender: true }
  },
  colorMode: {
    classSuffix: '',
  },
  runtimeConfig: {
    public: {
      backendUrl,
      apiUrl,
      appClientUrl,
    },
    backendUrl,
    apiUrl,
  },
});

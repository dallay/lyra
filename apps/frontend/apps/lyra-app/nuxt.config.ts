const { API_URL, BACKEND_URL } = process.env;
const backendUrl = BACKEND_URL || 'http://localhost:8080';
const apiUrl = API_URL || 'http://localhost:8080';


// https://nuxt.com/docs/api/configuration/nuxt-config
// biome-ignore lint/correctness/noUndeclaredVariables: Biome needs support for Vue/Nuxt.
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
    "@nuxt/scripts"
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
    },
    backendUrl,
    apiUrl,
  },
});

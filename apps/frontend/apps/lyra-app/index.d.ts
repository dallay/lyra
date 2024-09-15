declare module 'nuxt/schema' {
  interface RuntimeConfig {
  }
  interface PublicRuntimeConfig {
    apiUrl: string,
    backendUrl: string,
  }
}

export {}

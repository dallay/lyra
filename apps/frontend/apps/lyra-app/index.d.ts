declare module 'nuxt/schema' {
  interface RuntimeConfig {
    tiptapCollabSecret: string,
  }
  interface PublicRuntimeConfig {
    apiUrl: string,
    backendUrl: string,
    collabDocPrefix: string,
    tiptapCollabAppId: string,
  }
}

export {}

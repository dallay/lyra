declare module '#app' {
  interface NuxtApp {
    $dependenciesContainer: import('inversify').Container;
    $authController: import('@lyra/api-services').HttpAuthenticationService;
  }
}

declare module 'vue' {
  interface ComponentCustomProperties {
    $dependenciesContainer: import('inversify').Container;
    $authController: import('@lyra/api-services').HttpAuthenticationService;
  }
}

declare module 'nuxt/schema' {
  interface RuntimeConfig {
  }
  interface PublicRuntimeConfig {
    apiUrl: string,
    backendUrl: string,
  }
}

export {}

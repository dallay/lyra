import { defineStore } from 'pinia';
import { useNuxtApp, useRuntimeConfig } from '#imports';
import { jwtDecode } from "jwt-decode";
import type { AccessToken } from '@lyra/api-services';

interface UserPayloadInterface {
  identifier: string;
  password: string;
}
interface AuthStoreInterface {
  apiUrl: string;
  accessToken: AccessToken | null | undefined;
  loading: boolean;
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthStoreInterface => {
    const config = useRuntimeConfig();
    return ({
      apiUrl: config.public.apiUrl,
      accessToken: null,
      loading: false,
    });
  },
  getters: {
    isAuthenticated: (state) => state.accessToken !== null,
  },
  actions: {
    async authenticateUser({ identifier, password }: UserPayloadInterface) {
      this.loading = true;
      try {
        const {$api} = useNuxtApp();

        const token = await $api.auth.authenticate(identifier, password);

        this.accessToken = token || null;

      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async refreshToken() {
      this.loading = true;
      try {
        const {$api} = useNuxtApp();
        const token = await $api.auth.refreshToken();
        this.accessToken = token || null;
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async user() {
      // get the user data from the access token (jwt) and return it
      if (!this.accessToken) return null;
      return jwtDecode(this.accessToken.token);
    },
    async logUserOut() {
      // const { $authController } = useNuxtApp();
      this.accessToken = null; // set access token to null
      // await $authController.logout();
    },
  },
});

import { defineStore } from 'pinia';
import { useNuxtApp, useRuntimeConfig } from '#imports';
import { jwtDecode, type JwtPayload } from "jwt-decode";
import type { AccessToken } from '@lyra/api-services';
import type {IUser} from "~/types/model";

interface UserPayloadInterface {
  identifier: string;
  password: string;
}
interface AuthStoreInterface {
  apiUrl: string;
  accessToken: AccessToken | null | undefined;
  loading: boolean;
}

export interface JwtDecodePayload extends JwtPayload {
  name: string;
  preferred_username: string;
  email: string;
  realm_access?: {
    roles: string[];
  };
}
function convertJwtToUser(jwt: JwtDecodePayload): IUser | null {
  if (!jwt) return null;
  return {
    id: jwt.sub || '',
    name: jwt.name,
    username: jwt.preferred_username,
    email: jwt.email,
    roles: jwt.realm_access?.roles || []
  };
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
    async getUser(): Promise<IUser | null> {
      // get the user data from the access token (jwt) and return it
      if (!this.accessToken) return null;
      const jwt = jwtDecode<JwtDecodePayload>(this.accessToken.token);
      return convertJwtToUser(jwt);
    },
    async logUserOut() {
      this.accessToken = null;
      const {$api} = useNuxtApp();
      await $api.auth.logout();
    },
  },
});

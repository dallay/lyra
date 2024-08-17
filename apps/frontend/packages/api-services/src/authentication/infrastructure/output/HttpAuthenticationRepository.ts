import type AuthenticationService from "~/authentication/domain/AuthenticationService.ts";
import AccessToken from "~/authentication/domain/AccessToken.ts";
import {injectable} from "inversify";
import type LogoutAuthenticationService
  from "~/authentication/domain/LogoutAuthenticationService.ts";
import type {RequestService} from "~/request/RequestService.ts";

const error = new Error('Failed to login', { cause: 'Invalid credentials' });
@injectable()
export default class HttpAuthenticationRepository implements AuthenticationService, LogoutAuthenticationService {

  constructor(
    private readonly httpRequest: RequestService,
    private readonly headers: HeadersInit = {
      Accept: 'application/vnd.api.v1+json',
    },
  ) {
    /* ... */
  }

  async logout(): Promise<void> {
    // call the logout endpoint
  }

  async login(username: string, password: string): Promise<AccessToken> {
    if (!username || !password) {
      throw error;
    }
    const response = await this.httpRequest.request<AccessToken>(`/login`, {
      method: 'POST',
      body: JSON.stringify({ username, password }),
      headers: this.headers,
    });

    if (!response || !response.ok) {
      throw error;
    }

    return response._data || ({} as AccessToken);
  }

  async refreshToken(refreshToken: string): Promise<AccessToken> {
    if (!refreshToken) {
      throw new Error('Failed to refresh token', { cause: 'Invalid refresh token' });
    }
    const response = await this.httpRequest.request<AccessToken>('/refresh-token', {
      method: 'POST',
      // body: JSON.stringify({refreshToken}),
      headers: this.headers
    });

    if (!response || !response.ok) {
      throw new Error('Failed to refresh token', { cause: 'Invalid refresh token' });
    }

    return response._data || ({} as AccessToken);
  }
}

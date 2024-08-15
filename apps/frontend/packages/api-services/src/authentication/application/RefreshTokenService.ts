import AccessToken from '../domain/AccessToken';
import type AuthenticationService from "~/authentication/domain/AuthenticationService.ts";
import {inject, injectable} from "inversify";
import {HTTP_AUTHENTICATION_REPOSITORY} from "~/di/auth/auth.module.types.ts";

@injectable()
export default class RefreshTokenService {
  constructor(@inject(HTTP_AUTHENTICATION_REPOSITORY)  private readonly authService: AuthenticationService) {}

  async refreshToken(refreshToken: string): Promise<AccessToken> {
    return this.authService.refreshToken(refreshToken);
  }
}

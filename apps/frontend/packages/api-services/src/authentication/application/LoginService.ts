import AccessToken from '../domain/AccessToken';
import type AuthenticationService from "~/authentication/domain/AuthenticationService.ts";
import {inject, injectable} from "inversify";
import {HTTP_AUTHENTICATION_REPOSITORY} from "~/di/auth/auth.module.types.ts";

@injectable()
export default class LoginService {
  constructor(@inject(HTTP_AUTHENTICATION_REPOSITORY) private readonly authService: AuthenticationService) {}

  async loginWithUsernameAndPassword(username: string, password: string): Promise<AccessToken> {
    return this.authService.login(username, password);
  }
}

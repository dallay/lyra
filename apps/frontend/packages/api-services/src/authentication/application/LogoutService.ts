import {inject, injectable} from "inversify";
import {
  HTTP_LOGOUT_AUTHENTICATION_REPOSITORY
} from "~/di/auth/auth.module.types.ts";
import type LogoutAuthenticationService
  from "~/authentication/domain/LogoutAuthenticationService.ts";

@injectable()
export default class LogoutService {
  constructor(@inject(HTTP_LOGOUT_AUTHENTICATION_REPOSITORY) private readonly logoutService: LogoutAuthenticationService) {}

  async logout(): Promise<void> {
    return this.logoutService.logout();
  }
}

import {inject, injectable} from 'inversify';
import type LoginService from '~/authentication/application/LoginService.ts';
import {LOGIN_SERVICE, LOGOUT_SERVICE, REFRESH_TOKEN_SERVICE} from '~/di/auth/auth.module.types.ts';
import LoginRequest from '~/authentication/infrastructure/input/request/LoginRequest.ts';
import type RefreshTokenService from "~/authentication/application/RefreshTokenService.ts";
import type LogoutService from "~/authentication/application/LogoutService.ts";

@injectable()
export default class HttpAuthenticationService {
	constructor(@inject(LOGIN_SERVICE) private readonly loginService: LoginService,
              @inject(REFRESH_TOKEN_SERVICE) private readonly refreshTokenService: RefreshTokenService,
              @inject(LOGOUT_SERVICE) private readonly logoutService: LogoutService
              ) {
		/* ... */
	}
	async login(request: LoginRequest) {
		return this.loginService.loginWithUsernameAndPassword(request.username, request.password);
	}
  async refreshToken(refreshToken: string) {
    return this.refreshTokenService.refreshToken(refreshToken);
  }
  async logout() {
    return this.logoutService.logout();
  }
}

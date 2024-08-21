import { ContainerModule, type interfaces } from 'inversify';

import {
  HTTP_AUTHENTICATION_REPOSITORY,
  HTTP_LOGOUT_AUTHENTICATION_REPOSITORY,
  HTTP_AUTHENTICATION_SERVICE,
  LOGIN_SERVICE,
  LOGOUT_SERVICE,
  REFRESH_TOKEN_SERVICE,
} from '~/di/auth/auth.module.types.ts';
import type AuthenticationService from "~/authentication/domain/AuthenticationService.ts";
import HttpAuthenticationRepository
  from "~/authentication/infrastructure/output/HttpAuthenticationRepository.ts";
import LoginService from "~/authentication/application/LoginService.ts";
import LogoutService from "~/authentication/application/LogoutService.ts";
import RefreshTokenService from "~/authentication/application/RefreshTokenService.ts";
import HttpAuthenticationService
  from "~/authentication/infrastructure/input/HttpAuthenticationService.ts";
import type AccessToken from '~/authentication/domain/AccessToken';
import type LogoutAuthenticationService
  from "~/authentication/domain/LogoutAuthenticationService.ts";
import {REQUEST_SERVICE, type RequestService} from "~/request/RequestService.ts";
import {HttpRequestService} from "~/request/HttpRequestService.ts";

export const createAuthModule = (apiUrl: string) => new ContainerModule((bind: interfaces.Bind) => {
  bind<RequestService>(REQUEST_SERVICE).toDynamicValue((_) => {
    return new HttpRequestService(apiUrl);
  }).inSingletonScope();

  bind<HttpAuthenticationRepository>(HTTP_AUTHENTICATION_REPOSITORY).toDynamicValue((context) => {
    const requestService = context.container.get<RequestService>(REQUEST_SERVICE);
    return new HttpAuthenticationRepository(requestService);
  }).inSingletonScope();
  bind<LogoutAuthenticationService>(HTTP_LOGOUT_AUTHENTICATION_REPOSITORY).toDynamicValue((context) => {
    const requestService = context.container.get<RequestService>(REQUEST_SERVICE);
    return new HttpAuthenticationRepository(requestService);
  }).inSingletonScope();

  bind<LoginService>(LOGIN_SERVICE).to(LoginService);
  bind<LogoutService>(LOGOUT_SERVICE).to(LogoutService);
  bind<RefreshTokenService>(REFRESH_TOKEN_SERVICE).to(RefreshTokenService);
  bind<HttpAuthenticationService>(HTTP_AUTHENTICATION_SERVICE).to(HttpAuthenticationService);
});

export type { AuthenticationService, AccessToken };
export {
  HttpAuthenticationRepository,
  LoginService,
  RefreshTokenService,
  LogoutService,
  HttpAuthenticationService
};

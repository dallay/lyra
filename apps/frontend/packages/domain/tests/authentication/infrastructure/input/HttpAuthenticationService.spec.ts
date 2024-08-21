import 'reflect-metadata';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { mock, type MockProxy } from 'vitest-mock-extended';
import HttpAuthenticationService from '~/authentication/infrastructure/input/HttpAuthenticationService.ts';
import type LoginService from '~/authentication/application/LoginService.ts';
import type RefreshTokenService from '~/authentication/application/RefreshTokenService.ts';
import type LogoutService from '~/authentication/application/LogoutService.ts';
import LoginRequest from '~/authentication/infrastructure/input/request/LoginRequest.ts';
import type { AccessToken } from '~/authentication/domain/AccessToken.ts';

vi.mock('~/request/request.helper.ts', () => ({
  getAccessToken: vi.fn(),
}));

describe('HttpAuthenticationService', () => {
  let loginServiceMock: MockProxy<LoginService>;
  let refreshTokenServiceMock: MockProxy<RefreshTokenService>;
  let logoutServiceMock: MockProxy<LogoutService>;
  let httpAuthenticationService: HttpAuthenticationService;

  beforeEach(() => {
    loginServiceMock = mock<LoginService>();
    refreshTokenServiceMock = mock<RefreshTokenService>();
    logoutServiceMock = mock<LogoutService>();
    httpAuthenticationService = new HttpAuthenticationService(
      loginServiceMock,
      refreshTokenServiceMock,
      logoutServiceMock
    );
  });

  it('should call loginService with correct parameters', async () => {
    const request = new LoginRequest('username', 'password');
    loginServiceMock.loginWithUsernameAndPassword.mockResolvedValue('token');

    const result = await httpAuthenticationService.login(request);

    expect(loginServiceMock.loginWithUsernameAndPassword).toHaveBeenCalledWith('username', 'password');
    expect(result).toBe('token');
  });

  it('should throw an error if loginService fails', async () => {
    const request = new LoginRequest('username', 'password');
    loginServiceMock.loginWithUsernameAndPassword.mockRejectedValue(new Error('Login failed'));

    await expect(httpAuthenticationService.login(request)).rejects.toThrow('Login failed');
  });

  it('should call refreshTokenService with correct parameters', async () => {
    const refreshToken = 'testrefresh';
    refreshTokenServiceMock.refreshToken.mockResolvedValue('newToken');

    const result = await httpAuthenticationService.refreshToken(refreshToken);

    expect(refreshTokenServiceMock.refreshToken).toHaveBeenCalledWith('testrefresh');
    expect(result).toBe('newToken');
  });

  it('should throw an error if refreshTokenService fails', async () => {
    const refreshToken = 'testrefresh';
    refreshTokenServiceMock.refreshToken.mockRejectedValue(new Error('Refresh token failed'));

    await expect(httpAuthenticationService.refreshToken(refreshToken)).rejects.toThrow('Refresh token failed');
  });

  it('should call logoutService.logout and return the result', async () => {
    logoutServiceMock.logout.mockResolvedValue();

    const result = await httpAuthenticationService.logout();

    expect(logoutServiceMock.logout).toHaveBeenCalled();
    expect(result).toBe(undefined);
  });

  it('should throw an error if logoutService.logout fails', async () => {
    logoutServiceMock.logout.mockRejectedValue(new Error('Logout failed'));

    await expect(httpAuthenticationService.logout()).rejects.toThrow('Logout failed');
  });
});

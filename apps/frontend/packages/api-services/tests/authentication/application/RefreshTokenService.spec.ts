import 'reflect-metadata';
import { describe, it, expect,beforeEach } from 'vitest';
import { mock, MockProxy } from 'vitest-mock-extended';
import RefreshTokenService from '~/authentication/application/RefreshTokenService.ts';
import type AuthenticationService from '~/authentication/domain/AuthenticationService.ts';
import AccessToken from '~/authentication/domain/AccessToken.ts';

const error = new Error('Failed to refresh token', { cause: 'Invalid refresh token' })
describe('RefreshTokenService', () => {
  let refreshTokenService: RefreshTokenService;
  let authService: MockProxy<AuthenticationService>;

  beforeEach(() => {
    authService = mock<AuthenticationService>();
    refreshTokenService = new RefreshTokenService(authService);
  });

  it('refreshes token successfully with valid refresh token', async () => {
    const refreshToken = 'validRefreshToken';
    const accessToken = new AccessToken('validAccessToken');
    authService.refreshToken.mockResolvedValue(accessToken);

    const result = await refreshTokenService.refreshToken(refreshToken);

    expect(result).toBe(accessToken);
    expect(authService.refreshToken).toHaveBeenCalledWith(refreshToken);
  });

  it('throws an error when refresh token is invalid', async () => {
    const refreshToken = 'invalidRefreshToken';
    authService.refreshToken.mockRejectedValue(new Error('Invalid refresh token'));

    await expect(refreshTokenService.refreshToken(refreshToken)).rejects.toThrow('Invalid refresh token');
    expect(authService.refreshToken).toHaveBeenCalledWith(refreshToken);
  });

  it('throws an error when refresh token is empty', async () => {
    const refreshToken = '';

    authService.refreshToken.mockRejectedValue(error);

    await expect(refreshTokenService.refreshToken(refreshToken)).rejects.toThrow(error);
  });
});

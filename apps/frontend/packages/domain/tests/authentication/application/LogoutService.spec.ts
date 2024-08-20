import 'reflect-metadata';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { mock, type MockProxy } from 'vitest-mock-extended';
import LogoutService from '~/authentication/application/LogoutService.ts';
import type LogoutAuthenticationService from '~/authentication/domain/LogoutAuthenticationService.ts';

describe('LogoutService', () => {
  let logoutServiceMock: MockProxy<LogoutAuthenticationService>;
  let logoutService: LogoutService;

  beforeEach(() => {
    logoutServiceMock = mock<LogoutAuthenticationService>();
    logoutService = new LogoutService(logoutServiceMock);
  });

  it('should call logoutService.logout', async () => {
    logoutServiceMock.logout.mockResolvedValueOnce();

    await logoutService.logout();

    expect(logoutServiceMock.logout).toHaveBeenCalled();
  });

  it('should throw an error if logoutService.logout fails', async () => {
    logoutServiceMock.logout.mockRejectedValueOnce(new Error('Logout failed'));

    await expect(logoutService.logout()).rejects.toThrow('Logout failed');
  });
});

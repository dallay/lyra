import 'reflect-metadata';
import { type MockProxy, mock } from 'vitest-mock-extended';
import { describe, it, expect,beforeEach } from 'vitest';
import  LoginService from '@/authentication/application/LoginService';
import type AuthenticationService from '@/authentication/domain/AuthenticationService';
import AccessToken from '@/authentication/domain/AccessToken';

const error = new Error('Failed to login', { cause: 'Invalid credentials' });
describe('LoginService', () => {
  let loginService: LoginService;
  let authService: MockProxy<AuthenticationService>;

  beforeEach(() => {
    authService = mock<AuthenticationService>();
    loginService = new LoginService(authService);
  });

  it('logs in successfully with valid username and password', async () => {
    const username = 'validUser';
    const password = 'validPassword';
    const accessToken = new AccessToken('validToken');
    authService.login.mockResolvedValue(accessToken);

    const result = await loginService.loginWithUsernameAndPassword(username, password);

    expect(result).toBe(accessToken);
    expect(authService.login).toHaveBeenCalledWith(username, password);
  });

  it('throws an error when login fails', async () => {
    const username = 'invalidUser';
    const password = 'invalidPassword';
    authService.login.mockRejectedValue(new Error('Login failed'));

    await expect(loginService.loginWithUsernameAndPassword(username, password)).rejects.toThrow('Login failed');
    expect(authService.login).toHaveBeenCalledWith(username, password);
  });

  it('throws an error when username is empty', async () => {
    const username = '';
    const password = 'somePassword';

    // mock the login call
    authService.login.mockRejectedValue(error);

    await expect(loginService.loginWithUsernameAndPassword(username, password)).rejects.toThrow(error);
  });

  it('throws an error when password is empty', async () => {
    const username = 'someUser';
    const password = '';

    // mock the login call
    authService.login.mockRejectedValue(error);

    await expect(loginService.loginWithUsernameAndPassword(username, password)).rejects.toThrow(error);
  });
});

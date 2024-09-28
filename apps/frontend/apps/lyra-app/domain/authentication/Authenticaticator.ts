import type AccessToken from './AccessToken';

/**
 * Interface representing an authentication service.
 */
export default interface Authenticaticator {
  /**
   * Log in with the given username and password.
   * @param {string} username - The username of the user.
   * @param {string} password - The password of the user.
   * @returns {Promise<AccessToken>} A promise that resolves to an access token.
   */
  login(username: string, password: string): Promise<AccessToken>;

  /**
   * Refresh the access token using the given refresh token.
   * @param {string} refreshToken - The refresh token.
   * @returns {Promise<AccessToken>} A promise that resolves to a new access token.
   */
  refreshToken(refreshToken: string): Promise<AccessToken>;
}

/**
 * Interface representing a service for logging out the user.
 */
export default interface LogoutAuthenticationService {
  /**
   * Log out the user.
   * @returns {Promise<void>} A promise that resolves when the user is logged out.
   */
  logout(): Promise<void>;
}

import gravatarUrl from 'gravatar-url';

/**
 * Generates a Gravatar URL for the given email.
 *
 * @param {string} email - The email address to generate the Gravatar URL for.
 * @param {number} [size=100] - The size of the Gravatar image. Defaults to 100 if not provided.
 * @returns {string} The Gravatar URL for the given email and size.
 */
export default function (email: string, size: number = 100): string {
  return gravatarUrl(email, { size });
}

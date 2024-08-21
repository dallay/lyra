/**
 * Generates initials from a full name.
 *
 * @param {string} fullName - The full name to generate initials from.
 * @returns {string} The initials of the given full name.
 */
export default function (fullName: string): string {
  if (!fullName || fullName.length === 0) {
    return '';
  }
  return fullName.split(' ').map((n) => n[0]).join('');
}

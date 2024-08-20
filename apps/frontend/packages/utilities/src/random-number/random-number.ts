/**
 * Generates a random integer between the specified start and end values, inclusive.
 *
 * @param {number} start - The lower bound of the range.
 * @param {number} end - The upper bound of the range.
 * @returns {number} A random integer between start and end, inclusive.
 */
export default function (start: number, end: number): number {
  return Math.floor(Math.random() * (end - start + 1)) + start;
}

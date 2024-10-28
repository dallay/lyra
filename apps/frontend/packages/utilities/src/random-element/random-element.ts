/**
 * Returns a random element from the provided array.
 *
 * @template T - The type of elements in the array.
 * @param {Array<T>} array - The array from which to select a random element.
 * @returns {T} A random element from the array.
 */
export function randomElement<T>(array: Array<T>): T {
  return array[Math.floor(Math.random() * array.length)]
}

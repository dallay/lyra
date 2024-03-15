/**
 * The `remove` function modifies the original array by removing elements that satisfy the provided predicate function.
 * It returns a new array containing the removed elements.
 *
 * @template T - The type of elements in the array.
 * @param {T[]} array - The original array to be modified.
 * @param {(value: T, index: number, array: T[]) => boolean} predicate - The function invoked per iteration.
 * @returns {T[]} - A new array of removed elements.
 *
 * @example
 * const array = [1, 2, 3, 4];
 * const removed = remove(array, (n) => n % 2 === 0);
 * console.log(array); // Output: [1, 3]
 * console.log(removed); // Output: [2, 4]
 */
export function remove<T>(
	array: T[],
	predicate: (value: T, index: number, array: T[]) => boolean
): T[] {
	const removed = array.filter((value, index, arr) => predicate(value, index, arr));
	const remaining = array.filter((value, index, arr) => !predicate(value, index, arr));
	array.length = 0;
	array.push(...remaining);
	return removed;
}

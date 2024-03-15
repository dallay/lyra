/**
 * Sorts an array of objects based on a single key or a key getter function.
 *
 * @param array - The array to sort.
 * @param keyOrGetter - The key of the object to sort by or a function that returns the key.
 * @returns A new array sorted by the specified key or key getter function.
 *
 * @example
 * sortBy([{name: 'banana'}, {name: 'apple'}], 'name');
 * // Returns: [{name: 'apple'}, {name: 'banana'}]
 *
 * sortBy([{name: 'banana', age: 2}, {name: 'apple', age: 1}], item => item.age);
 * // Returns: [{name: 'apple', age: 1}, {name: 'banana', age: 2}]
 */
export function sortBy<T>(array: T[], keyOrGetter: keyof T | ((item: T) => any)): T[] {
	const keyGetter =
		typeof keyOrGetter === 'function' ? keyOrGetter : (item: T) => item[keyOrGetter as keyof T];

	return [...array].sort((a, b) => {
		const keyA = keyGetter(a);
		const keyB = keyGetter(b);
		return keyA > keyB ? 1 : keyA < keyB ? -1 : 0;
	});
}

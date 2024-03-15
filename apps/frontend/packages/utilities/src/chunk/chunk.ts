/**
 * Divides an array into groups of the specified size. If the array cannot be evenly divided,
 * the last group will be of the remaining size.
 *
 * @param input - The array to divide into groups.
 * @param size - The size of each group.
 * @returns A new array with the groups.
 *
 * @example
 * chunk(['a', 'b', 'c', 'd'], 2); // Returns: [['a', 'b'], ['c', 'd']]
 * chunk(['a', 'b', 'c', 'd'], 3); // Returns: [['a', 'b', 'c'], ['d']]
 */
export function chunk<T>(input: T[], size: number): T[][] {
	return input.reduce<T[][]>((arr, item, idx) => {
		return idx % size === 0 ? [...arr, [item]] : [...arr.slice(0, -1), [...arr.slice(-1)[0], item]];
	}, []);
}

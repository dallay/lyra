/**
 * Creates an array of elements split into groups the length of size.
 * @param {T[]} input The array to process.
 * @param {number} size The length of each chunk.
 * @returns {T[][]} An array of chunks.
 *
 * @example
 * console.log(chunk(['a', 'b', 'c', 'd'], 2));
 * // => [['a', 'b'], ['c', 'd']]
 *
 * @example
 * console.log(chunk(['a', 'b', 'c', 'd'], 3));
 * // => [['a', 'b', 'c'], ['d']]
 */
export const chunk = <T>(input: T[], size: number): T[][] => {
	return input.reduce((arr: T[][], item: T, idx: number) => {
		return idx % size === 0 ? [...arr, [item]] : [...arr.slice(0, -1), [...arr.slice(-1)[0], item]];
	}, []);
};

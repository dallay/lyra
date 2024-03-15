/**
 * Groups the elements of an array based on the given key getter function.
 *
 * @param list - The array to group.
 * @param keyGetter - The function that defines the key to group by.
 * @returns An object where the keys are the group keys and the values are arrays of elements in that group.
 *
 * @example
 * groupBy(['one', 'two', 'three'], item => item.length);
 * // Returns: {3: ["one", "two"], 5: ["three"]}
 *
 * groupBy([1.3, 2.1, 2.4], num => Math.floor(num));
 * // Returns: {1: [1.3], 2: [2.1, 2.4]}
 */
export function groupBy<T, K extends keyof any>(
	list: T[],
	keyGetter: (input: T) => K
): Record<K, T[]> {
	return list.reduce(
		(previous, currentItem) => {
			const group = keyGetter(currentItem);
			if (!previous[group]) previous[group] = [];
			previous[group].push(currentItem);
			return previous;
		},
		{} as Record<K, T[]>
	);
}

/**
 * Type definition for order. It can be either 'asc' for ascending order or 'desc' for descending order.
 */
export type Order = 'asc' | 'desc';

/**
 * Sorts an array of objects based on multiple keys and orders.
 *
 * @param array - The array to sort.
 * @param keys - The keys of the object to sort by.
 * @param orders - The order for each key. It should be either 'asc' for ascending order or 'desc' for descending order.
 * @returns A new array sorted by the specified keys and orders.
 *
 * @example
 * orderBy([{name: 'banana', type: 'fruit'}, {name: 'apple', type: 'fruit'}], ['type', 'name'], ['asc', 'desc']);
 * // Returns: [{name: 'banana', type: 'fruit'}, {name: 'apple', type: 'fruit'}]
 */
export function orderBy<T>(array: T[], keys: (keyof T)[], orders: Order[]): T[] {
	return [...array].sort((a, b) => {
		for (let i = 0; i < keys.length; i++) {
			if (a[keys[i]] > b[keys[i]]) return orders[i] === 'asc' ? 1 : -1;
			if (a[keys[i]] < b[keys[i]]) return orders[i] === 'asc' ? -1 : 1;
		}
		return 0;
	});
}

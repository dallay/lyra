/**
 * Sorts an array of object based on an object key provided by a parameter.
 * @param key The key to sort the array of objects by.
 * @returns A function that compares two objects based on the key provided.
 *
 * @example
 * const fruits = [
 *   {name:"banana", amount: 2},
 *   {name:"apple", amount: 4},
 *   {name:"pineapple", amount: 2},
 *   {name:"mango", amount: 1}
 * ];
 *
 * fruits.concat().sort(sortBy("name"));
 * // => [{name:"apple", amount: 4}, {name:"banana", amount: 2}, {name:"mango", amount: 1}, {name:"pineapple", amount: 2}]
 */
export const sortBy = <T>(key: keyof T) => {
	return (a: T, b: T) => (a[key] > b[key] ? 1 : b[key] > a[key] ? -1 : 0);
};

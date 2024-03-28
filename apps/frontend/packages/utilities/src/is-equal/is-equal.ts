/**
 * Performs a deep comparison between two values to determine if they are equivalent.
 *
 * This function works by comparing the values directly if they are not objects.
 * If they are objects, it compares their keys and their corresponding values.
 * If the values of a key are also objects, it recursively calls `isEqual` to compare them.
 *
 * Note: This function is a simplified version and may not cover all edge cases that lodash's `isEqual` function does.
 * For example, it does not handle comparisons for functions, DOM nodes, Maps, Sets, etc.
 * If you need to handle these cases, you might want to consider sticking with lodash's `isEqual` or another library that provides deep equality checks.
 *
 * @param {unknown} value - The first value to compare.
 * @param {unknown} other - The second value to compare.
 * @returns {boolean} - Returns `true` if the values are equivalent, else `false`.
 */
export function isEqual(value: unknown, other: unknown): boolean {
	if (value === other) return true;

	if (typeof value !== 'object' || value === null || typeof other !== 'object' || other === null) {
		return false;
	}

	const keysA = Object.keys(value as object),
		keysB = Object.keys(other as object);

	if (keysA.length !== keysB.length) return false;

	for (const key of keysA) {
		if (!keysB.includes(key)) return false;
		if (typeof (value as any)[key] === 'object' && typeof (other as any)[key] === 'object') {
			if (!isEqual((value as any)[key], (other as any)[key])) return false;
		} else {
			if ((value as any)[key] !== (other as any)[key]) return false;
		}
	}

	return true;
}

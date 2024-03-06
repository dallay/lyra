interface MyObject {
	[key: string]: unknown;
}
/**
 * The base implementation of `pick` which doesn't validate or coerce arguments.
 *
 * @private
 * @param {Object} object The source object.
 * @param {string[]} paths The property paths to pick.
 * @returns {Object} Returns the new object.
 */
function basePick(object: MyObject, paths: string[]): MyObject {
	object = Object(object);
	return paths.reduce((result: MyObject, path: string) => {
		if (path in object) {
			result[path] = object[path];
		}
		return result;
	}, {});
}

/**
 * Creates an object composed of the picked `object` properties.
 *
 * @since 0.1.0
 * @category Object
 * @param {Object} object The source object.
 * @param {...(string|string[])} [paths] The property paths to pick.
 * @returns {Object} Returns the new object.
 * @example
 *
 * const object = { 'a': 1, 'b': '2', 'c': 3 }
 *
 * pick(object, ['a', 'c'])
 * // => { 'a': 1, 'c': 3 }
 */

function pick(object: MyObject | null, ...paths: string[]): MyObject {
	return object == null ? {} : basePick(object, paths);
}

export default pick;

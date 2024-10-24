/**
 * Creates a debounced function that delays invoking the provided function until after
 * a specified wait time has elapsed since the last time the debounced function was called.
 * Optionally, the function can be invoked immediately on the leading edge instead of the trailing.
 *
 * @template T - The type of the function to debounce.
 * @param {T} func - The function to debounce.
 * @param {number} wait - The number of milliseconds to delay.
 * @param {boolean} [immediate] - If `true`, triggers the function on the leading edge, instead of the trailing.
 * @returns {DebouncedFunction<T>} - Returns the new debounced function.
 */
type DebouncedFunction<T extends (...args: any[]) => any> = (
  ...args: Parameters<T>
) => void;

export function debounce<T extends (...args: any[]) => any>(
  func: T,
  wait: number,
  immediate?: boolean
): DebouncedFunction<T> {
  let timeout: ReturnType<typeof setTimeout> | null;

  return function (this: ThisParameterType<T>, ...args: Parameters<T>) {
    const context = this;

    if (timeout) {
      clearTimeout(timeout);
    }

    if (immediate && !timeout) {
      func.apply(context, args);
    }

    timeout = setTimeout(() => {
      timeout = null;
      if (!immediate) {
        func.apply(context, args);
      }
    }, wait);
  };
}

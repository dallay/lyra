import { describe, it, expect, vi } from 'vitest';
import { debounce } from './debounce';

describe('debounce', () => {
  it('should delay the execution of the function', () => {
    vi.useFakeTimers();
    const func = vi.fn();
    const debouncedFunc = debounce(func, 1000);

    debouncedFunc();
    expect(func).not.toHaveBeenCalled();

    vi.advanceTimersByTime(1000);
    expect(func).toHaveBeenCalled();
  });

  it('should execute the function immediately if immediate is true', () => {
    vi.useFakeTimers();
    const func = vi.fn();
    const debouncedFunc = debounce(func, 1000, true);

    debouncedFunc();
    expect(func).toHaveBeenCalled();

    vi.advanceTimersByTime(1000);
    expect(func).toHaveBeenCalledTimes(1);
  });

  it('should reset the timer if called again before the wait time', () => {
    vi.useFakeTimers();
    const func = vi.fn();
    const debouncedFunc = debounce(func, 1000);

    debouncedFunc();
    vi.advanceTimersByTime(500);
    debouncedFunc();
    vi.advanceTimersByTime(500);
    expect(func).not.toHaveBeenCalled();

    vi.advanceTimersByTime(500);
    expect(func).toHaveBeenCalled();
  });

  it('should use the correct context when calling the function', () => {
    vi.useFakeTimers();
    const context = { value: 42 };
    const func = vi.fn(function (this: typeof context) {
      return this.value;
    });
    const debouncedFunc = debounce(func.bind(context), 1000);

    debouncedFunc();
    vi.advanceTimersByTime(1000);
    expect(func).toHaveReturnedWith(42);
  });
});

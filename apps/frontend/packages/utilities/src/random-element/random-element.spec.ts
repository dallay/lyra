import { describe, it, expect } from 'vitest';
import { randomElement } from './random-element';

describe('randomElement', () => {
    it('should return an element from the array', () => {
        const array = [1, 2, 3, 4, 5];
        const element = randomElement(array);
        expect(array).toContain(element);
    });

    it('should return undefined for an empty array', () => {
        const array: number[] = [];
        const element = randomElement(array);
        expect(element).toBeUndefined();
    });

    it('should return the only element for a single-element array', () => {
        const array = [42];
        const element = randomElement(array);
        expect(element).toBe(42);
    });

    it('should handle arrays with different types', () => {
        const array = ['a', 1, true, null];
        const element = randomElement(array);
        expect(array).toContain(element);
    });
});

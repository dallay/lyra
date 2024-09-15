import { describe, it, expect } from 'vitest';
import { InvalidArgumentError } from '@lyra/shared';
import HexColor from './HexColor';

describe('HexColor', () => {
	it('creates HexColor from valid hexadecimal color', () => {
		const validColor = '#FFFFFF';
		const hexColor = HexColor.create(validColor);
		expect(hexColor.value).toEqual(validColor);
	});

	it('throws error when creating HexColor from invalid hexadecimal color', () => {
		const invalidColor = '#ZZZZZZ';
		expect(() => HexColor.create(invalidColor)).toThrow(InvalidArgumentError);
	});

	it('creates HexColor from valid short hexadecimal color', () => {
		const validShortColor = '#FFF';
		const hexColor = HexColor.create(validShortColor);
		expect(hexColor.value).toEqual(validShortColor);
	});

	it('throws error when creating HexColor from invalid short hexadecimal color', () => {
		const invalidShortColor = '#ZZZ';
		expect(() => HexColor.create(invalidShortColor)).toThrow(InvalidArgumentError);
	});
});

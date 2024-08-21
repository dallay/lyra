import randomNumber from './random-number';

describe('randomNumber', () => {
  it('returns a number within the specified range', () => {
    const start = 1;
    const end = 10;
    const result = randomNumber(start, end);
    expect(result).toBeGreaterThanOrEqual(start);
    expect(result).toBeLessThanOrEqual(end);
  });

  it('returns the start number when start and end are the same', () => {
    const start = 5;
    const end = 5;
    const result = randomNumber(start, end);
    expect(result).toBe(start);
  });

  it('handles negative ranges correctly', () => {
    const start = -10;
    const end = -1;
    const result = randomNumber(start, end);
    expect(result).toBeGreaterThanOrEqual(start);
    expect(result).toBeLessThanOrEqual(end);
  });

  it('handles ranges that include zero', () => {
    const start = -5;
    const end = 5;
    const result = randomNumber(start, end);
    expect(result).toBeGreaterThanOrEqual(start);
    expect(result).toBeLessThanOrEqual(end);
  });

  it('returns a number within a large range', () => {
    const start = 1;
    const end = 1000000;
    const result = randomNumber(start, end);
    expect(result).toBeGreaterThanOrEqual(start);
    expect(result).toBeLessThanOrEqual(end);
  });
});

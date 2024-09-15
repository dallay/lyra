import formatDate from './format-date';

describe('formatDate', () => {
  it('formats a valid date string correctly', () => {
    const date = '2023-10-01';
    const result = formatDate(date);
    expect(result).toBe('October 1, 2023');
  });

  it('formats a valid Date object correctly', () => {
    const date = new Date('2023-10-01');
    const result = formatDate(date);
    expect(result).toBe('October 1, 2023');
  });

  it('returns an empty string for undefined date', () => {
    const result = formatDate(undefined);
    expect(result).toBe('');
  });

  it('returns an empty string for invalid date string', () => {
    const date = 'invalid-date';
    const result = formatDate(date);
    expect(result).toBe('');
  });

  it('formats date correctly for different locales', () => {
    const originalLanguage = navigator.language;
    Object.defineProperty(navigator, 'language', { value: 'fr-FR', configurable: true });
    const date = '2023-10-01';
    const result = formatDate(date);
    expect(result).toBe('1 octobre 2023');
    Object.defineProperty(navigator, 'language', { value: originalLanguage, configurable: true });
  });
});

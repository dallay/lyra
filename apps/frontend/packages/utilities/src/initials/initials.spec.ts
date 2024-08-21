import initials from './initials';

describe('initials', () => {
  it('returns initials for a single name', () => {
    const result = initials('John');
    expect(result).toBe('J');
  });

  it('returns initials for a full name', () => {
    const result = initials('John Doe');
    expect(result).toBe('JD');
  });

  it('returns initials for a name with multiple spaces', () => {
    const result = initials('John   Doe');
    expect(result).toBe('JD');
  });

  it('returns initials for a name with leading and trailing spaces', () => {
    const result = initials('  John Doe  ');
    expect(result).toBe('JD');
  });

  it('returns initials for a name with multiple words', () => {
    const result = initials('John Michael Doe');
    expect(result).toBe('JMD');
  });

  it('returns an empty string for an empty input', () => {
    const result = initials('');
    expect(result).toBe('');
  });

  it('returns initials for a name with hyphenated words', () => {
    const result = initials('Mary-Jane Watson');
    expect(result).toBe('MW');
  });

  it('returns initials for a name with special characters', () => {
    const result = initials('John O\'Connor');
    expect(result).toBe('JO');
  });

  it('returns initials for a name with non-alphabetic characters', () => {
    const result = initials('John123 Doe456');
    expect(result).toBe('JD');
  });
});

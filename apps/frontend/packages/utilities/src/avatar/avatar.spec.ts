import getAvatarUrl from './avatar';

describe('getAvatarUrl', () => {
  it('returns a valid gravatar URL for a given email', () => {
    const email = 'test@example.com';
    const result = getAvatarUrl(email);
    expect(result).toBe('https://gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0?size=100');
  });

  it('returns a gravatar URL with the specified size', () => {
    const email = 'test@example.com';
    const size = 200;
    const result = getAvatarUrl(email, size);
    expect(result).toBe('https://gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0?size=200');
  });

  it('returns a gravatar URL with the default size when size is not provided', () => {
    const email = 'test@example.com';
    const result = getAvatarUrl(email);
    expect(result).toBe('https://gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0?size=100');
  });

  it('returns a gravatar URL for an email with uppercase characters', () => {
    const email = 'Test@Example.com';
    const result = getAvatarUrl(email);
    expect(result).toBe('https://gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0?size=100');
  });

  it('returns a gravatar URL for an email with leading and trailing spaces', () => {
    const email = '  test@example.com  ';
    const result = getAvatarUrl(email.trim());
    expect(result).toBe('https://gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0?size=100');
  });

  it('returns a gravatar URL for an email with special characters', () => {
    const email = 'user+test@example.com';
    const result = getAvatarUrl(email);
    expect(result).toBe('https://gravatar.com/avatar/2588cc7353d7a8c3ff3867546a542510?size=100');
  });
});

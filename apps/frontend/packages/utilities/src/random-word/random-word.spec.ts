import { describe, it, expect } from 'vitest';
import generateRandomWords from './random-word';

describe('generateRandomWords', () => {
  it('returns a string with the default number of words when no arguments are provided', () => {
    const result = generateRandomWords();
    expect(result.split(' ').length).toBe(2);
  });

  it('returns a string with the specified number of words', () => {
    const result = generateRandomWords(undefined, 3);
    expect(result.split(' ').length).toBe(3);
  });

  it('returns a string with words from the provided word list', () => {
    const customWords = ['apple', 'banana', 'cherry'];
    const result = generateRandomWords(customWords, 2);
    const words = result.split(' ');
    expect(customWords).toContain(words[0]);
    expect(customWords).toContain(words[1]);
  });

  it('returns an empty string when count is zero', () => {
    const result = generateRandomWords(undefined, 0);
    expect(result).toBe('');
  });

  it('handles a word list with a single word correctly', () => {
    const singleWordList = ['unique'];
    const result = generateRandomWords(singleWordList, 3);
    expect(result).toBe('unique unique unique');
  });

  it('handles an empty word list by returning an empty string', () => {
    const result = generateRandomWords([], 3);
    expect(result).toBe('');
  });
});

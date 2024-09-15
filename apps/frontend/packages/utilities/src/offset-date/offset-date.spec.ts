import { describe, it, expect } from 'vitest';
import offsetDate from './offset-date';

describe('offsetDate', () => {
  it('returns the current date when no offset is provided', () => {
    const today = new Date().toISOString().split('T')[0];
    expect(offsetDate()).toBe(today);
  });

  it('returns the correct date when a positive offset is provided', () => {
    const date = new Date();
    date.setMonth(date.getMonth() + 2);
    const expectedDate = date.toISOString().split('T')[0];
    expect(offsetDate(2)).toBe(expectedDate);
  });

  it('returns the correct date when a negative offset is provided', () => {
    const date = new Date();
    date.setMonth(date.getMonth() - 3);
    const expectedDate = date.toISOString().split('T')[0];
    expect(offsetDate(-3)).toBe(expectedDate);
  });

  it('handles year change correctly when offset is positive', () => {
    const date = new Date('2023-11-01');
    date.setMonth(date.getMonth() + 2);
    const expectedDate = date.toISOString().split('T')[0];
    expect(offsetDate(2, new Date('2023-11-01'))).toBe(expectedDate);
  });

  it('handles year change correctly when offset is negative', () => {
    const date = new Date('2023-01-01');
    date.setMonth(date.getMonth() - 2);
    const expectedDate = date.toISOString().split('T')[0];
    expect(offsetDate(-2, new Date('2023-01-01'))).toBe(expectedDate);
  });

  it('handles leap year correctly', () => {
    const date = new Date('2024-02-29');
    date.setMonth(date.getMonth() + 1);
    const expectedDate = date.toISOString().split('T')[0];
    expect(offsetDate(1, new Date('2024-02-29'))).toBe(expectedDate);
  });
});

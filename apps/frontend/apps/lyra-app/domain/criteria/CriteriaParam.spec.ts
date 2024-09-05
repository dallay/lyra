import { describe, expect, it} from "vitest";
import { toQueryParams, type CriteriaParam } from './CriteriaParam';
import type { SortParam } from './SortParam';

describe('toQueryParams', () => {
  it('should convert filter criteria to query params', () => {
    const filterCriteria: CriteriaParam[] = [
      { column: 'age', operator: 'gt', values: ['30'] },
      { column: 'name', operator: 'eq', values: ['John'] },
    ];
    const result = toQueryParams({ filterCriteria, size: 10 });
    expect(result).toEqual({
      'filter[age]': 'gt:30',
      'filter[name]': 'eq:John',
      'size': '10',
    });
  });

  it('should convert search criteria to query params', () => {
    const searchCriteria: CriteriaParam[] = [
      { column: 'description', operator: 'ne', values: ['test'] },
    ];
    const result = toQueryParams({ searchCriteria, size: 10 });
    expect(result).toEqual({
      'search[description]': 'ne:test',
      'size': '10',
    });
  });

  it('should handle multiple values for a single criterion', () => {
    const filterCriteria: CriteriaParam[] = [
      { column: 'status', operator: 'eq', values: ['active', 'pending'] },
    ];
    const result = toQueryParams({ filterCriteria, size: 10 });
    expect(result).toEqual({
      'filter[status]': 'eq:active,eq:pending',
      'size': '10',
    });
  });

  it('should merge values for the same column', () => {
    const filterCriteria: CriteriaParam[] = [
      { column: 'age', operator: 'gt', values: ['30'] },
      { column: 'age', operator: 'lt', values: ['50'] },
    ];
    const result = toQueryParams({ filterCriteria, size: 10 });
    expect(result).toEqual({
      'filter[age]': 'gt:30,lt:50',
      'size': '10',
    });
  });

  it('should return an empty object if no criteria are provided', () => {
    const result = toQueryParams({ size: 10 });
    expect(result).toEqual({ 'size': '10' });
  });

  it('should handle both filter and search criteria', () => {
    const filterCriteria: CriteriaParam[] = [
      { column: 'age', operator: 'gt', values: ['30'] },
    ];
    const searchCriteria: CriteriaParam[] = [
      { column: 'description', operator: 'ne', values: ['test'] },
    ];
    const result = toQueryParams({ filterCriteria, searchCriteria, size: 10 });
    expect(result).toEqual({
      'filter[age]': 'gt:30',
      'search[description]': 'ne:test',
      'size': '10',
    });
  });

  it('should handle sort criteria', () => {
    const sortCriteria: SortParam[] = [
      { field: 'name', direction: 'asc' },
      { field: 'age', direction: 'desc' },
    ];
    const result = toQueryParams({ sortCriteria, size: 10 });
    expect(result).toEqual({
      'sort': ['asc:name', 'desc:age'],
      'size': '10',
    });
  });

  it('should handle pagination with cursor', () => {
    const result = toQueryParams({ size: 20, cursor: 'cursor123' });
    expect(result).toEqual({
      'size': '20',
      'cursor': 'cursor123',
    });
  });
});

import {describe, expect, it} from "vitest";
import {type CriteriaParam, toQueryParams} from './CriteriaParam';
import type {SortParam} from './SortParam';

describe('toQueryParams', () => {
  it('should convert filter criteria to query params', () => {
    const filterCriteria: Set<CriteriaParam> = new Set([
      { column: 'age',  values: [{operator: 'gt',value:'30'}] },
      { column: 'name',  values: [{operator: 'eq', value:'John'}] },
    ]);
    const result = toQueryParams({ filterCriteria, size: 10 });
    expect(result).toEqual({
      'filter[age]': 'gt:30',
      'filter[name]': 'eq:John',
      'size': '10',
    });
  });

  it('should convert filter criteria to query params with logical operator', () => {
    const filterCriteria: Set<CriteriaParam> = new Set([
      { column: 'status', logicalOperator: 'OR', values: [{operator: 'eq', value:'BLOCKLISTED'},{operator: 'eq', value: 'DISABLED'}] },
      { column: 'createdAt', logicalOperator: 'AND', values: [{operator: 'gte', value:'2024-03-23T23:00:00.000Z'}, {operator: 'lte',value:'2024-09-07T23:00:00.000Z'}] },
    ]);
    const result = toQueryParams({ filterCriteria, size: 10 });
    expect(result).toEqual({
      'filter[status]': 'OR:eq:BLOCKLISTED,eq:DISABLED',
      'filter[createdAt]': 'AND:gte:2024-03-23T23:00:00.000Z,lte:2024-09-07T23:00:00.000Z',
      'size': '10',
    });
  });

  it('should convert search criteria to query params', () => {
    const searchCriteria: Set<CriteriaParam> = new Set([
      { column: 'description',  values: [{operator: 'ne',value:'test'}] },
    ]);
    const result = toQueryParams({ searchCriteria, size: 10 });
    expect(result).toEqual({
      'search[description]': 'ne:test',
      'size': '10',
    });
  });

  it('should handle multiple values for a single criterion', () => {
    const filterCriteria: Set<CriteriaParam> = new Set([
      { column: 'status',  values: [{operator: 'eq',value:'active'}, {operator: 'eq',value:'pending'}] },
    ]);
    const result = toQueryParams({ filterCriteria, size: 10 });
    expect(result).toEqual({
      'filter[status]': 'eq:active,eq:pending',
      'size': '10',
    });
  });

  it('should merge values for the same column', () => {
    const filterCriteria: Set<CriteriaParam> = new Set([
      { column: 'age',  values: [{operator: 'gt',value:'30'}] },
      { column: 'age',  values: [{operator: 'lt',value:'50'}] },
    ]);
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
    const filterCriteria: Set<CriteriaParam> = new Set([
      { column: 'age',  values: [{operator: 'gt',value:'30'}] },
    ]);
    const searchCriteria: Set<CriteriaParam> = new Set([
      { column: 'description', values: [{operator: 'ne', value:'test'}] },
    ]);
    const result = toQueryParams({ filterCriteria, searchCriteria, size: 10 });
    expect(result).toEqual({
      'filter[age]': 'gt:30',
      'search[description]': 'ne:test',
      'size': '10',
    });
  });

  it('should handle sort criteria', () => {
    const sortCriteria: Set<SortParam> = new Set([
      { field: 'name', direction: 'asc' },
      { field: 'age', direction: 'desc' },
    ]);
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

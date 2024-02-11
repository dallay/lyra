
import { expect, test } from 'vitest';
import { BasicSort, Sort, SortType } from '../../src';
test('constructs with default sort type', () => {
  const sort: Sort = new BasicSort('field')
  expect(sort.field).toBe('field')
  expect(sort.type).toBe(SortType.ASC)
})

test('constructs with provided sort type', () => {
  const sort = new BasicSort('field', SortType.DESC)
  expect(sort.field).toBe('field')
  expect(sort.type).toBe(SortType.DESC)
})

test('generates correct query string for ascending sort', () => {
  const sort = new BasicSort('field', SortType.ASC)
  expect(sort.toQueryString()).toBe('sort=asc:field')
})

test('generates correct query string for descending sort', () => {
  const sort = new BasicSort('field', SortType.DESC)
  expect(sort.toQueryString()).toBe('sort=desc:field')
})

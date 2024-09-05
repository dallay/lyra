import type { SortParam } from './SortParam';

/**
 * Type representing the possible operators for criteria.
 */
export type CriteriaOperator = 'eq' | 'ne' | 'lt' | 'gt' | 'lte' | 'gte';

/**
 * Interface representing a single criteria parameter.
 */
export interface CriteriaParam {
  column: string;
  operator: CriteriaOperator;
  values: string[];
}

/**
 * Interface representing the query parameters for criteria.
 */
export interface CriteriaQueryParams {
  filterCriteria?: CriteriaParam[];
  searchCriteria?: CriteriaParam[];
  sortCriteria?: SortParam[];
  size: number;
  cursor?: string;
}

/** Default page size for query parameters. */
export const DEFAULT_PAGE_SIZE = 10;

/**
 * Converts criteria query parameters to a query string object.
 *
 * @param {CriteriaQueryParams} criteria - The criteria query parameters.
 * @returns {Record<string, string | string[]>} The query string object.
 */
export function toQueryParams(criteria: CriteriaQueryParams): Record<string, string | string[]> {
  const params: Record<string, string | string[]> = {};

  const { filterCriteria = [], searchCriteria = [], sortCriteria = [], size = DEFAULT_PAGE_SIZE, cursor } = criteria;

  /**
   * Adds criteria to the query parameters.
   *
   * @param {CriteriaParam[]} criteria - The criteria to add.
   * @param {string} prefix - The prefix for the query key.
   */
  const addCriteriaToParams = (criteria: CriteriaParam[], prefix: string) => {
    criteria.forEach(({ column, operator, values }) => {
      const queryKey = `${prefix}[${column}]`;
      const value = values.map((val) => `${operator}:${val}`).join(',');
      params[queryKey] = params[queryKey] ? `${params[queryKey]},${value}` : value;
    });
  };

  addCriteriaToParams(filterCriteria, 'filter');
  addCriteriaToParams(searchCriteria, 'search');

  if (sortCriteria.length > 0) {
    params['sort'] = sortCriteria
      .filter(({ field }) => field)
      .map(({ direction, field }) => `${direction}:${field}`);
  }

  params['size'] = size.toString();
  if (cursor) params['cursor'] = cursor;

  return params;
}

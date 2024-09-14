import type { SortParam } from "./SortParam";
import {ArrayStack, type Stack} from "@lyra/shared";

/**
 * Type representing the possible operators for criteria.
 */
export type CriteriaOperator = "eq" | "ne" | "lt" | "gt" | "lte" | "gte";
/**
 * Type representing the possible operators for criteria.
 */
export type LogicalOperator = "AND" | "OR";

/**
 * Interface representing a single sort parameter.
 */
export type CriteriaParamValue = { operator: CriteriaOperator; value: string };

/**
 * Interface representing a single criteria parameter.
 */
export interface CriteriaParam {
  column: string;
  logicalOperator?: LogicalOperator;
  values: CriteriaParamValue[];
}
export interface Cursor {
  currentPageCursor: string | null,
  prevPageCursor: string | null,
  nextPageCursor: string | null,
}
/**
 * Interface representing the query parameters for criteria.
 */
export interface CriteriaQueryParams {
  filterCriteria?: Set<CriteriaParam>;
  search?: string;
  sortCriteria?: Set<SortParam>;
  size: number;
  cursor?: string | null;
}

/** Default page size for query parameters. */
export const DEFAULT_PAGE_SIZE = 10;

/**
 * Converts criteria query parameters to a query string object.
 *
 * @param {CriteriaQueryParams} criteria - The criteria query parameters.
 * @returns {Record<string, string | string[]>} The query string object.
 */
export function toQueryParams(
  criteria: CriteriaQueryParams
): Record<string, string | string[]> {
  const params: Record<string, string | string[]> = {};

  const {
    filterCriteria = new Set<CriteriaParam>(),
    search = "",
    sortCriteria = new Set<SortParam>(),
    size = DEFAULT_PAGE_SIZE,
    cursor,
  } = criteria;

  /**
   * Adds criteria to the query parameters.
   *
   * @param {Set<CriteriaParam>} criteria - The criteria to add.
   * @param {string} prefix - The prefix for the query key.
   */
  const addCriteriaToParams = (
    criteria: Set<CriteriaParam>,
    prefix: string
  ) => {
    criteria.forEach(({ column, logicalOperator, values }) => {
      const queryKey = `${prefix}[${column}]`;
      const value = values
        .map((val) => `${val.operator}:${val.value}`)
        .join(",");
      const logicalOpPrefix = logicalOperator ? `${logicalOperator}:` : "";
      params[queryKey] = params[queryKey]
        ? `${params[queryKey]},${logicalOpPrefix}${value}`
        : `${logicalOpPrefix}${value}`;
    });
  };
  console.log("BEFORE ADD THE FILTER CRITERIA", filterCriteria)
  addCriteriaToParams(filterCriteria, "filter");
  if (search) {
    params["search"] = search;
  }
  if (Array.from(sortCriteria).length > 0) {
    params["sort"] = Array.from(sortCriteria)
      .filter(({ field }) => field)
      .map(({ direction, field }) => `${direction}:${field}`);
  }

  params["size"] = size.toString();
  if (cursor) params["cursor"] = cursor;

  return params;
}

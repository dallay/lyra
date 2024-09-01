import type { SearchParameters } from 'ofetch';

export type SortType = 'asc' | 'desc';

export type Sort = {
  field?: string;
  direction: SortType;
};

export type QuerySort = Sort & {
  toQueryString: () => string;
};

export interface PageResponse<T> {
  data: T[];
  nextPageCursor?: string;
}

export type CriteriaParam =
  | { search?: string | undefined; filter?: string | undefined }
  | {
      search?: string;
      filter?: string;
    };

export const buildParams = (
  criteria?: CriteriaParam,
  sort?: QuerySort,
  size = 10,
  cursor?: string
): SearchParameters => {
  const params: SearchParameters = {};
  if (criteria?.search) {
    params.search = criteria.search;
  }
  if (criteria?.filter) {
    const filters = criteria.filter.split('&');
    for (const filter of filters) {
      const [key, value] = filter.split('=');
      if (params[`filter[${key}]`]) {
        params[`filter[${key}]`] += `,${value}`;
      } else {
        params[`filter[${key}]`] = value;
      }
    }
  }
  if (sort) {
    const sortQuery = sort.toQueryString();
    const [key, value] = sortQuery.split('=');
    params[key] = value;
  }
  params.size = size.toString();
  if (cursor) {
    params.cursor = cursor;
  }
  return params;
};

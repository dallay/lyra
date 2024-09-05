export type SortType = 'asc' | 'desc';

export type SortParam = {
  field?: string;
  direction: SortType;
};

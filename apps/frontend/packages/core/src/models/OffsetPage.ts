export interface OffsetPage<T> {
	data: T[];
	total?: number;
	perPage: number;
	page?: number;
	totalPages?: number;
}

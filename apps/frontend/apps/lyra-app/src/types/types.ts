export interface PageResponse<T> {
	data: T[];
	nextPageCursor?: string;
}

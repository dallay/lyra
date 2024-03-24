import type { QuerySort, SortType } from '@lyra/ui';

export interface PageResponse<T> {
	data: T[];
	nextPageCursor?: string;
}

export interface Subscriber {
	id: string;
	email: string;
	name: string;
	status: string;
	createdAt: string;
	updatedAt: string;
}

export interface Subscribers {
	subscribers: Subscriber[];
}

export class BasicQuerySort implements QuerySort {
	field: string;
	direction: SortType;

	constructor(field: string, type: SortType = 'asc') {
		this.field = field;
		this.direction = type;
	}

	toQueryString(): string {
		return `sort=${this.direction}:${this.field}`;
	}
}

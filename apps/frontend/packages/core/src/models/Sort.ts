export enum SortType {
	ASC = 'asc',
	DESC = 'desc',
}

export interface Sort {
	field: string;
	type: SortType;
	toQueryString: () => string;
}

export class BasicSort implements Sort {
	field: string;
	type: SortType;

	constructor(field: string, type: SortType = SortType.ASC) {
		this.field = field;
		this.type = type;
	}

	toQueryString(): string {
		return `sort=${this.type}:${this.field}`;
	}
}

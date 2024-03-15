export type ColumnItem = {
	key: string;
	name: string;
	sortable?: boolean;
	spanable?: boolean;
	sticky?: string | 'left' | 'right';
	class?: unknown;
	classColumn?: unknown;
	classCell?: unknown;
};

export type PaginationType = 'offset' | 'cursor';

export type Control = {
	paginationType: PaginationType;
	sort?: {
		field?: string;
		direction: SortType;
	};
	offset?: OffsetPage;
	cursor?: CursorPage;
};

export type SortType = 'asc' | 'desc';

export type OffsetPage = {
	rows: number;
	page: number;
};

export type CursorPage = {
	cursor: string;
	limit: number;
};
export const defaultOffsetPage: OffsetPage = {
	rows: 10,
	page: 1,
};

export const defaultCursorPage: CursorPage = {
	cursor: '',
	limit: 10,
};

export const defaultControl: Control = {
	paginationType: 'offset',
	sort: {
		field: 'createdAt',
		direction: 'desc',
	},
	offset: defaultOffsetPage,
	cursor: defaultCursorPage,
};

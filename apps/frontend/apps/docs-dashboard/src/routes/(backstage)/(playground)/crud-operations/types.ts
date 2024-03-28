import { type Control } from '@lyra/ui';

export type TodoItem = {
	_id?: string;
	title?: string;
	completed?: boolean;
};

export type State = {
	searchForm: {
		title?: string;
		filter?: number;
	};

	todosLoading: boolean;
	todosRows: TodoItem[];
	todosCount: number;
	todosControl: Control;

	deleteDialog: boolean;
	deleteExpected: TodoItem['title'];
	deleteContent: TodoItem;
	deleteLoading: boolean;
};

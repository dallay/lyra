import { chunk, sortBy } from '@lyra/utilities';
import { Control } from '~/components/table/types';

const defaultField = 'createdAt';
const defaultControl: Control = {
	paginationType: 'offset',
	sort: {
		field: defaultField,
		direction: 'desc',
	},
	offset: {
		rows: 10,
		page: 1,
	},
};

function offsetPagination<T>(control: Control, arr: T[]) {
	const offset = control.offset;
	const chunked = chunk(arr, offset?.rows ?? 10);
	const page = (offset?.page ?? 1) - 1;
	return chunked[page];
}

function cursorPagination<T extends Record<string, any>>(control: Control, arr: T[]) {
	const cursor = control.cursor;
	const limit = cursor?.limit ?? 10;
	// find all the items that are before (greater) the cursor and then take the next limit items
	const cursorIndex = arr.findIndex((item) => item['id'] == cursor?.cursor);
	const page = cursorIndex === -1 ? 0 : cursorIndex + 1;
	return arr.slice(page, page + limit);
}

export default <T extends Record<string, any>>(rows: T[], control: Control = defaultControl) => {
	if (!rows?.length) return [];

	const sort = control.sort;
	let arr = [...rows];
	if (sort && sort.field) {
		const sortedArr = sortBy(arr, (item) => item[sort?.field ?? defaultField]);
		arr = sort.direction === 'asc' ? sortedArr : sortedArr.reverse();
	}
	return control.paginationType === 'offset'
		? offsetPagination(control, arr)
		: cursorPagination(control, arr);
};

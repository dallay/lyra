import { sortBy, chunk } from '@/utils';

export default <T extends Record<string, any>>(
	rows: T[],
	control = { rows: 10, page: 1, field: 'createdAt', direction: 'desc' } as any
) => {
	if (!rows?.length) return [];

	let arr = [...rows];

	if (control.field && control.direction === 'asc') {
		arr.sort(sortBy(control.field));
	}

	if (control.field && control.direction === 'desc') {
		arr.sort(sortBy(control.field)).reverse();
	}

	const chunked = chunk(arr, control.rows);

	return chunked[control.page - 1];
};

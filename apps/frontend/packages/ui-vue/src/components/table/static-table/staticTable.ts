import { sortBy, chunk } from '@/utils';
import type { Control } from '@/components/table/types';
import type { ItemProps } from '@/components/table/Table.vue';

export default <T extends ItemProps>(
	rows: T[],
	control = { rows: 10, page: 1, field: 'createdAt', direction: 'desc' } as Control
) => {
	if (!rows?.length) return [];

	const arr = [...rows];

	if (control.field && control.direction === 'asc') {
		arr.sort(sortBy(control.field));
	}

	if (control.field && control.direction === 'desc') {
		arr.sort(sortBy(control.field)).reverse();
	}

	const chunkSize = control.rows ? control.rows : 10;
	const chunked = chunk(arr, chunkSize);

	const pageIndex = control.page ? control.page - 1 : 0;
	return chunked[pageIndex];
};

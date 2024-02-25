import type { App } from 'vue';
import {
	OffsetPagination,
	CursorPagination,
	BaseInput,
	BasicDropdown,
	type ColumnInfo,
	type SortedEvent,
	GeneralFilter,
	SvgIcon,
	useGenericDataTable,
	useGenericSelectInput,
} from '@/components';
import '@lyra/css-config/index.css';

export default {
	install: (app: App) => {
		app.component('BaseInput', BaseInput);
		app.component('OffsetPagination', OffsetPagination);
		app.component('CursorPagination', CursorPagination);
		app.component('SvgIcon', SvgIcon);
		app.component('BasicDropdown', BasicDropdown);
		app.component('GeneralFilter', GeneralFilter);
	},
};
export type { ColumnInfo, SortedEvent };
export {
	BaseInput,
	OffsetPagination,
	CursorPagination,
	SvgIcon,
	BasicDropdown,
	GeneralFilter,
	useGenericDataTable,
	useGenericSelectInput,
};

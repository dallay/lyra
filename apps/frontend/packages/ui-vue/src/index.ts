import type { App } from 'vue';
import {
	BasePagination,
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
		app.component('BasePagination', BasePagination);
		app.component('SvgIcon', SvgIcon);
		app.component('BasicDropdown', BasicDropdown);
		app.component('GeneralFilter', GeneralFilter);
	},
};
export type { ColumnInfo, SortedEvent };
export {
	BaseInput,
	BasePagination,
	SvgIcon,
	BasicDropdown,
	GeneralFilter,
	useGenericDataTable,
	useGenericSelectInput,
};

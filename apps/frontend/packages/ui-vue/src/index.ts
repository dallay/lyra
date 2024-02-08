import type { App } from 'vue';
import {
	BasicCounter,
	BasicDropdown,
	type ColumnInfo,
	GeneralFilter,
	SvgIcon,
	useGenericDataTable,
	useGenericSelectInput,
} from '@/components';
import '@lyra/css-config/index.css';

export default {
	install: (app: App) => {
		app.component('BasicCounter', BasicCounter);
		app.component('SvgIcon', SvgIcon);
		app.component('BasicDropdown', BasicDropdown);
		app.component('GeneralFilter', GeneralFilter);
	},
};

export {
	BasicCounter,
	SvgIcon,
	BasicDropdown,
	GeneralFilter,
	useGenericDataTable,
	useGenericSelectInput,
	ColumnInfo,
};

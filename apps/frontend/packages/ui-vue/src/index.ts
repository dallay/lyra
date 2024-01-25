import type { App } from 'vue';
import {
	useGenericDataTable,
	useGenericSelectInput,
	BasicCounter,
	type ColumnInfo,
} from '@/components';
import '@lyra/css-config/index.css';

export default {
	install: (app: App) => {
		app.component('BasicCounter', BasicCounter);
	},
};

export { BasicCounter, useGenericDataTable, useGenericSelectInput, ColumnInfo };

import type { App } from 'vue';
import { BasicCounter } from '@/components';

export default {
	install: (app: App) => {
		app.component('BasicCounter', BasicCounter);
	},
};

export { BasicCounter };

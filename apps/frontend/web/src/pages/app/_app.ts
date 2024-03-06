import type { App } from 'vue';

export default (app: App) => {
	app.config.errorHandler = (err, vm, info) => {
		console.error('errorHandler', err, vm, info);
	};

	app.config.warnHandler = (msg, vm, trace) => {
		console.warn('warnHandler', msg, vm, trace);
	};
};

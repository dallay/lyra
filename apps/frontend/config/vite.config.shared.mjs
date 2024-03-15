/** @type {import("vite").UserConfig} */
import { resolve } from 'path';
import { codecovVitePlugin } from '@codecov/vite-plugin';
export const sharedViteConfig = (dirname) => ({
	resolve: {
		alias: {
			'~': resolve(dirname, 'src'),
			'@': resolve(dirname, 'src'),
			mock: resolve(dirname, '../mock/src/routes'),
		},
		mainFields: ['module'],
	},
	plugins: [
		codecovVitePlugin({
			enableBundleAnalysis: process.env.CODECOV_TOKEN !== undefined,
			bundleName: 'LyraUI',
			uploadToken: process.env.CODECOV_TOKEN,
		}),
	],
	test: {
		globals: true,
		environment: 'happy-dom',
	},
});

/** @type {import('vite').UserConfig} */
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import dts from 'vite-plugin-dts';
import { name, version } from './package.json';
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
	build: {
		lib: {
			entry: path.resolve(__dirname, 'src/components/index.ts'),
			fileName: 'index',
			formats: ['es', 'cjs', 'umd'],
			name: 'vue-ui',
		},
		outDir: './dist',
		rollupOptions: {
			// make sure to externalize deps that shouldn't be bundled
			// into your library
			input: {
				main: path.resolve(__dirname, 'src/index.ts'),
			},
			external: ['vue'],
			output: {
				assetFileNames: (assetInfo) => {
					if (assetInfo.name === 'main.css') return 'vue-ui-ts.min.css';
					return assetInfo.name;
				},
				exports: 'named',
				globals: {
					vue: 'Vue',
				},
			},
		},
	},
	define: {
		pkgJson: { name, version },
	},
	plugins: [vue(), dts()],
	resolve: {
		alias: {
			'@': path.resolve(__dirname, 'src'),
		},
	},
});

/** @type {import('vite').UserConfig} */
// astro.config.ts
import { resolve } from 'path';
import { defineConfig } from 'vite';
import dts from 'vite-plugin-dts';

import { name, version } from './package.json';

// https://vitejs.dev/guide/build.html#library-mode
export default defineConfig({
	build: {
		lib: {
			entry: resolve(__dirname, 'src/index.ts'),
			fileName: 'index',
			name: 'core',
		},
		outDir: './dist',
	},
	define: {
		pkgJson: { name, version },
	},
	plugins: [dts()],
});

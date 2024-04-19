import dts from 'vite-plugin-dts';
import { defineConfig, mergeConfig } from 'vite';
import { resolve } from 'path';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';

/** @type {import('vite').UserConfig} */
export default defineConfig(
	mergeConfig(sharedViteConfig(__dirname), {
		build: {
			lib: {
				entry: resolve(__dirname, 'src/index.ts'),
				name: 'utilities',
				formats: ['es'],
			},
			target: 'esnext', // transpile as little as possible
		},
		plugins: [dts({ rollupTypes: true })],
	})
);

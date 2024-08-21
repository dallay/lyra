import dts from 'vite-plugin-dts';
import { defineConfig, mergeConfig } from 'vite';
import { resolve } from 'node:path';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';
import * as path from 'node:path';

/** @type {import('vite').UserConfig} */
export default defineConfig(
	mergeConfig(sharedViteConfig(__dirname), {
		build: {
			lib: {
				entry: resolve(__dirname, 'src/index.ts'),
				name: '@lyra/domain',
				formats: ['es'],
			},
			target: 'esnext', // transpile as little as possible
		},
		plugins: [dts({ rollupTypes: true })],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src')
      },
    },
    setupFiles: ['./setupVitest.js'],
	})
);

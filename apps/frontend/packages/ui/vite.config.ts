/** @type {import('vite').UserConfig} */
import Vue from '@vitejs/plugin-vue';
import dts from 'vite-plugin-dts';
import unocss from 'unocss/vite';
import { defineConfig, mergeConfig } from 'vite';
import { resolve } from 'path';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';
export default mergeConfig(
	sharedViteConfig(__dirname),
	defineConfig({
		build: {
			cssCodeSplit: true,
			target: 'esnext',
			lib: {
				entry: resolve(__dirname, './src/index.ts'),
				fileName: 'lyra-vue-ui',
				// formats: ['es'],
				name: 'lyra-vue-ui',
			},
			rollupOptions: {
				external: ['vue'],
				output: {
					globals: {
						vue: 'Vue',
					},
				},
			},
		},
		plugins: [Vue(), dts(), unocss({})],
	})
);

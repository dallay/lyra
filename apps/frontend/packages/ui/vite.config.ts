import vue from '@vitejs/plugin-vue';
import dts from 'vite-plugin-dts';
import unocss from 'unocss/vite';
import { defineConfig, mergeConfig } from 'vite';
import { resolve } from 'path';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';

/** @type {import('vite').UserConfig} */
export default defineConfig(
	mergeConfig(sharedViteConfig(__dirname), {
		build: {
			cssCodeSplit: true,
			target: 'esnext',
			lib: {
				entry: resolve(__dirname, './src/index.ts'),
				fileName: 'lyra-vue-ui',
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
		plugins: [vue(), dts(), unocss({})],
	})
);

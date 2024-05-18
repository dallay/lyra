import { URL, fileURLToPath } from 'node:url';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';
import Vue from '@vitejs/plugin-vue';
import envify from 'process-envify';
import unocss from 'unocss/vite';
import { defineConfig, mergeConfig } from 'vite';
import vueRoutes from 'vite-plugin-vue-routes';

export default defineConfig(
	mergeConfig(sharedViteConfig(__dirname), {
		define: envify({
			API_URL: process.env.API_URL || '',
		}),
		plugins: [Vue(), vueRoutes(), unocss({})],
		resolve: {
			alias: {
				'@': fileURLToPath(new URL('./src', import.meta.url)),
				'@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
				'@components': fileURLToPath(new URL('./src/components', import.meta.url)),
				'@views': fileURLToPath(new URL('./src/views', import.meta.url)),
				'@router': fileURLToPath(new URL('./src/router', import.meta.url)),
				'@store': fileURLToPath(new URL('./src/store', import.meta.url)),
				'@atoms': fileURLToPath(new URL('./src/components/atoms', import.meta.url)),
				'@molecules': fileURLToPath(new URL('./src/components/molecules', import.meta.url)),
				'@organisms': fileURLToPath(new URL('./src/components/organisms', import.meta.url)),
				'@templates': fileURLToPath(new URL('./src/components/templates', import.meta.url)),
			},
		},
		preview: {
			port: 5173,
		},
		server: {
			port: 5173,
			proxy: {
				'/api': {
					target: 'http://localhost:8080',
					ws: true,
				},
			},
		},
	})
);

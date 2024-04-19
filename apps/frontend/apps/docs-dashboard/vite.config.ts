import Vue from '@vitejs/plugin-vue';
import envify from 'process-envify';
import unocss from 'unocss/vite';
import { defineConfig, mergeConfig } from 'vite';
import vueRoutes from 'vite-plugin-vue-routes';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';

export default defineConfig(
	mergeConfig(sharedViteConfig(__dirname), {
		define: envify({
			API_URL: process.env.API_URL || '',
		}),
		plugins: [Vue(), vueRoutes(), unocss({})],
		preview: {
			port: 8979,
		},
		server: {
			port: 8978,
			proxy: {
				'/api': {
					target: 'http://127.0.0.1:3000',
					ws: true,
				},
			},
		},
	})
);

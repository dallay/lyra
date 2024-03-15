import Vue from '@vitejs/plugin-vue';
import envify from 'process-envify';
import unocss from 'unocss/vite';
import { defineConfig, mergeConfig } from 'vite';
import vueRoutes from 'vite-plugin-vue-routes';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';

export default mergeConfig(
	sharedViteConfig(__dirname),
	defineConfig({
		define: envify({
			API_URL: process.env.API_URL || '',
		}),
		plugins: [Vue(), vueRoutes(), unocss({})],
		server: {
			proxy: {
				'/api': {
					target: 'http://127.0.0.1:3000',
					ws: true,
				},
			},
		},
	})
);

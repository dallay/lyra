import envify from 'process-envify';
import { defineConfig, mergeConfig } from 'vite';
import fastify from 'vite-plugin-fastify';
import fastifyRoutes from 'vite-plugin-fastify-routes';
import { sharedViteConfig } from '@lyra/config/vite.config.shared';

export default mergeConfig(
	sharedViteConfig(__dirname),
	defineConfig({
		define: envify({
			HOST: process.env.HOST || '127.0.0.1',
			PORT: process.env.PORT || 3000,
		}),
		plugins: [fastify(), fastifyRoutes()],
	})
);

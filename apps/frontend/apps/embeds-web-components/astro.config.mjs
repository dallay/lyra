import { defineConfig } from 'astro/config';
import UnoCSS from 'unocss/astro';
import node from '@astrojs/node';
import envify from 'process-envify';
const DEV_PORT = 9876;

// https://astro.build/config
export default defineConfig({
	site: import.meta.env.DEV
		? `http://localhost:${DEV_PORT}`
		: 'https://embeds-web-components-lyra-landing-page.onrender.com',
	server: {
		/* Dev. server only */
		port: DEV_PORT,
	},
	integrations: [
		UnoCSS({
			injectReset: true,
		}),
	],
	output: 'server',
	adapter: node({
		mode: 'standalone',
	}),
	vite: {
		define: envify({
			API_URL: process.env.API_URL || `http://localhost:${DEV_PORT}`,
			BACKEND_URL: process.env.BACKEND_URL || `http://localhost:8080`,
		}),
	},
});

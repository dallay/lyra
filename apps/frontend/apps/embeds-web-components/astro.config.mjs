import { defineConfig } from 'astro/config';
import UnoCSS from 'unocss/astro';
import node from '@astrojs/node';
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
});

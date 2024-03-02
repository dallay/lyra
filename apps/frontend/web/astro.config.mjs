import { defineConfig } from 'astro/config';
import icon from 'astro-icon';
import unocss from 'unocss/astro'
import vue from '@astrojs/vue';
import { loadEnv } from 'vite';
import path from 'path';
import node from '@astrojs/node';

const DEV_PORT = 4321;
const envDir = path.resolve(process.cwd() + '/../../../');
console.log('🛸 envDir', envDir);
const { BACKEND_URL } = loadEnv(process.env.NODE_ENV, envDir, '');

console.log('🚀 BACKEND_URL', BACKEND_URL);

// https://astro.build/config
export default defineConfig({
	site: import.meta.env.DEV ? `http://localhost:${DEV_PORT}` : 'https://lyra-nwhm.onrender.com',
	outDir: '../build/www',
	integrations: [
		vue(),
    unocss({ injectReset: true, configFile: './uno.config.cjs' }),
		icon({
			iconDir: 'src/assets/icons',
			include: {
				'simple-icons': ['*'],
				ph: ['*'],
			},
		}),
	],
	output: 'server',
	adapter: node({
		mode: 'standalone',
	}),
	server: {
		port: DEV_PORT,
	},
	vite: {
		envDir: envDir,
		server: {
			proxy: {
				'/backend/api': {
					target: BACKEND_URL,
					changeOrigin: true,
					secure: false,
					rewrite: (path) => path.replace(/^\/backend\/api/, '/api/'),
				},
			},
		},
	},
});

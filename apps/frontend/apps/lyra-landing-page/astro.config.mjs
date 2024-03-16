import { defineConfig } from 'astro/config';
import UnoCSS from 'unocss/astro';
import icon from 'astro-icon';
import { loadEnv } from 'vite';
import path from 'path';

const DEV_PORT = 4321;
const envDir = path.resolve(process.cwd() + '/../../../');
console.log('🛸 envDir', envDir);
const { BACKEND_URL } = loadEnv(process.env.NODE_ENV, envDir, '');

console.log('🚀 BACKEND_URL', BACKEND_URL);

// https://astro.build/config
export default defineConfig({
	site: import.meta.env.DEV ? `http://localhost:${DEV_PORT}` : 'https://lyra-nwhm.onrender.com',
	integrations: [
		UnoCSS({ injectReset: true }),
		icon({
			iconDir: 'src/assets/icons',
			include: {
				'simple-icons': ['*'],
				ph: ['*'],
			},
		}),
	],
});

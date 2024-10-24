import { defineConfig } from 'astro/config';
import UnoCSS from 'unocss/astro';
import icon from 'astro-icon';
import { loadEnv } from 'vite';
import path from 'path';
import basicSsl from '@vitejs/plugin-basic-ssl';
import node from '@astrojs/node';

const DEV_PORT = 4321;
const envDir = path.resolve(process.cwd() + '/../../../');
console.log('🛸 envDir', envDir);
const { BACKEND_URL } = loadEnv(process.env.NODE_ENV, envDir, '');

console.log('🚀 BACKEND_URL', BACKEND_URL);

// https://astro.build/config
export default defineConfig({
    site: import.meta.env.DEV ? `http://localhost:${DEV_PORT}` : 'https://lyra-nwhm.onrender.com',
  output: 'hybrid',
  adapter: node({
    mode: 'standalone',
  }),
    integrations: [
        UnoCSS({
            injectReset: true,
        }),
        icon({
            iconDir: 'src/assets/icons',
            include: {
                'simple-icons': ['*'],
                ph: ['*'],
            },
        }),
    ],
    vite: {
    plugins: [basicSsl()],
    server: {
      https: true,
    },
    define: {
      'process.env.APP_CLIENT_URL': JSON.stringify(process.env.APP_CLIENT_URL || 'http://localhost:5173'),
    },
    },
});

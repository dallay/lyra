/** @type {import('vite').UserConfig} */
import path from 'path';
import { defineConfig } from 'vite';
import dts from 'vite-plugin-dts';

// https://vitejs.dev/config/
export default defineConfig({
  build: {
    rollupOptions: {
      input: path.resolve(__dirname, 'src/index.ts'), // specify the entry point
    },
  },
  plugins: [ dts()],
});

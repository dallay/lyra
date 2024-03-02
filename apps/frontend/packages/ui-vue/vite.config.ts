/** @type {import('vite').UserConfig} */
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import dts from 'vite-plugin-dts';
import pkg from './package.json';
import { resolve } from 'path';

// https://vitejs.dev/config/
export default defineConfig({
	build: {
    cssCodeSplit: true,
    target: 'esnext',
		lib: {
      entry: resolve(__dirname, './src/index.ts'),
			// fileName: 'index',
			// formats: ['es'],
			name: 'vue-ui',
		},
		// outDir: './dist',
    rollupOptions: {
      // make sure to externalize deps that shouldn't be bundled
      // into your library
      external: ['vue'],
      output: {
        // Provide global variables to use in the UMD build
        // for externalized deps
        globals: {
          vue: 'Vue',
        },
      },
    }
	},
	define: {
		pkgJson: { name:pkg.name, version:pkg.version },
	},
	plugins: [vue(), dts()],
  resolve: {
    alias: {
      '~': resolve(__dirname, './src'),
      '@': resolve(__dirname, './src'),
    },
    dedupe: ['vue'],
  },
});

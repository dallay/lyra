import { defineConfig } from 'astro/config';
import tailwind from '@astrojs/tailwind';
import icon from 'astro-icon';

import vue from '@astrojs/vue';

// https://astro.build/config
export default defineConfig({
	outDir: '../build/www',
	integrations: [
		tailwind(),
		vue(),
		icon({
			iconDir: 'src/assets/icons',
			include: {
				'simple-icons': ['*'],
				ph: ['*'],
			},
		}),
	],
});

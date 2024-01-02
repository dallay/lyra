import { defineConfig } from 'astro/config';
import tailwind from '@astrojs/tailwind';

import icon from 'astro-icon';

// https://astro.build/config
export default defineConfig({
	output: 'static',
	outDir: '../build/www',
	integrations: [
		tailwind(),
		icon({
			iconDir: 'src/assets/icons',
			include: {
				'simple-icons': ['*'],
				ph: ['*'],
			},
		}),
	],
});

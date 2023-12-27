import { defineConfig } from 'astro/config';

import tailwind from "@astrojs/tailwind";

// https://astro.build/config
export default defineConfig({
  srcDir: './src/main/webapp/src',
  publicDir: './src/main/webapp/public',
  root: './src/main/webapp',
  outDir: './build/resources/main/static',
  integrations: [tailwind()]
});

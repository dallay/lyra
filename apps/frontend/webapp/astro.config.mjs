import { defineConfig } from "astro/config";
import tailwind from "@astrojs/tailwind";

import icon from "astro-icon";

// https://astro.build/config
export default defineConfig({
  output: "static",
  srcDir: "./src/main/webapp/src",
  publicDir: "./src/main/webapp/public",
  root: "./src/main/webapp",
  outDir: "./build/www",
  integrations: [
    tailwind(),
    icon({
      iconDir: "./src/main/webapp/src/assets/icons",
      include: {
        "simple-icons": ["*"],
        ph: ["*"],
      },
    }),
  ],
});

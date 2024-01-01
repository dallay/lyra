// tailwind config is required for editor support
import sharedConfig from "../packages/css-config/tailwind.config.mts";
import type { Config } from "tailwindcss";

const config: Pick<Config, "presets"> = {
	presets: [sharedConfig],
};

export default config;

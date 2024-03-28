import { defineConfig } from '@playwright/test';

const url = 'http://localhost:4173';
export default defineConfig({
	testMatch: ['**/*.spec.e2e.ts'],
	use: {
		baseURL: url,
	},
	webServer: {
		command: 'npm run preview',
		url: url,
		timeout: 120 * 1000,
		reuseExistingServer: !process.env.CI,
	},
});

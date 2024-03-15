import { mergeConfig } from 'vite';
import { sharedViteConfig } from './vite.config.shared.mjs';
export const sharedVitestConfig = (dirname) =>
	mergeConfig(sharedViteConfig(dirname), {
		test: {
			// run the tests inside the folder __tests__ and skip the ones .spec.e2e files
			include: ['**/__tests__/**/*.spec.{ts,js}', '**/*.spec.{ts,js}'],
			exclude: ['tests/e2e/**/*.spec.e2e.{ts,js}'],
			testMatch: ['**/__tests__/**/*.spec.{ts,js}'],
			coverage: {
				all: true,
				provider: 'v8',
				enabled: true,
				reportsDirectory: './coverage',
			},
			reporters: ['default', 'json', 'verbose', ['junit', { suiteName: 'UI tests' }]],
			outputFile: {
				junit: './coverage/junit-report.xml',
				json: './coverage/json-report.json',
			},
		},
	});

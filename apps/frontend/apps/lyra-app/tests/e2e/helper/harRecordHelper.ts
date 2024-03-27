import type { Browser, Page } from '@playwright/test';

export async function recordHar(
	browser: Browser,
	options: {
		path: string;
		fileName: string;
		urlFilter: string;
		func?: (page: Page) => Promise<void>;
	}
) {
	const { path, fileName, urlFilter, func } = options;
	const context = await browser.newContext({
		recordHar: {
			path: `${path}/hars/${fileName}.har`,
			mode: 'full',
			urlFilter: urlFilter,
		},
	});
	const page = await context.newPage();
	await func(page);
	await context.close();
}

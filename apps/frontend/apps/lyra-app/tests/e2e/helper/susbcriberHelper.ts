import { type Page } from '@playwright/test';

export async function getSubscribers(page: Page) {
	await page.routeFromHAR('tests/e2e/hars/subscribers.har', {
		url: '**/api/newsletter/subscribers**',
		update: false,
	});
	await page.goto('/audience/subscribers', { waitUntil: 'networkidle' });
}

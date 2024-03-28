import { expect, type Page } from '@playwright/test';

export async function signIn(
	options: { page: Page; startUrl: string } = { page: null, startUrl: '/' }
): Promise<void> {
	const { page, startUrl } = options;
	await page.goto(startUrl);
	await page.goto('/sign-in', { waitUntil: 'networkidle' });
	await expect(page.getByText('Sign in to our platform')).toBeVisible();
	await page.routeFromHAR('tests/e2e/hars/login.har', {
		url: '**/api/auth/sign-in',
		update: false,
	});
	await page.getByTestId('sign-in').click();
	await loadUserData({ page });
}

export async function loadUserData(options: { page: Page }): Promise<void> {
	const { page } = options;
	await page.routeFromHAR('tests/e2e/hars/user.har', {
		url: '**/api/auth/user',
		update: false,
	});
}

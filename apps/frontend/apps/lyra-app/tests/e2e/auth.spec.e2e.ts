import { expect, test } from '@playwright/test';
import { signIn } from './helper/authHelper';

test('Sign-in', async ({ page }) => {
	await signIn({ page, startUrl: '/' });

	await expect(page).toHaveURL(/.*dashboard/);
});

test('Sign-out', async ({ page }) => {
	await signIn({ page, startUrl: '/' });

	await page.getByTestId('avatar-menu').click();
	await expect(page.getByText('Yuniel Acosta')).toBeVisible();
	await expect(page.getByText('yap@example.com')).toBeVisible();

	await page
		.locator('div')
		.filter({ hasText: /^Sign out$/ })
		.first()
		.click();
	await expect(page).toHaveURL(/.*sign-in/);
});

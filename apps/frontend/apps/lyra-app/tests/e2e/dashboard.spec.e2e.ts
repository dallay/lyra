import { expect, test, type Page } from '@playwright/test';
import { signIn } from './helper/authHelper';

async function checkAppearance(
	page: Page,
	profileMenu = page.getByTestId('avatar-menu'),
	darkMode = false
) {
	await profileMenu.click();
	await expect(page.getByText('Yuniel Acosta')).toBeVisible();
	await expect(page.getByText('yap@example.com')).toBeVisible();
	const themeSelector = page.getByTestId('theme-selector');
	await themeSelector.click();
	await page
		.locator('li')
		.filter({ hasText: darkMode ? 'Dark' : 'Light' })
		.click();
	await expect(page.locator('html').first()).toHaveClass(`h-full ${darkMode ? 'dark' : 'light'}`);
	await page
		.locator('div')
		.filter({ hasText: /^YAAppearance Setting applies to this browser only LightDarkSystem$/ })
		.getByRole('button')
		.click();
}

test('Dashboard and Dark mode', async ({ page }) => {
	await signIn({ page, startUrl: '/' });
	await expect(page).toHaveURL(/.*dashboard/);

	await page.goto('/dashboard', { waitUntil: 'networkidle' });
	const profileMenu = page.getByTestId('avatar-menu');
	await checkAppearance(page, profileMenu, true);
	await page
		.locator('div')
		.filter({ hasText: /^Sign out$/ })
		.first()
		.click();
	await expect(page).toHaveURL(/.*sign-in/);
});

test('Dashboard and Light mode', async ({ page }) => {
	await signIn({ page, startUrl: '/' });
	await expect(page).toHaveURL(/.*dashboard/);

	await page.goto('/dashboard', { waitUntil: 'networkidle' });
	const profileMenu = page.getByTestId('avatar-menu');
	await checkAppearance(page, profileMenu, false);
	await page
		.locator('div')
		.filter({ hasText: /^Sign out$/ })
		.first()
		.click();
	await expect(page).toHaveURL(/.*sign-in/);
});

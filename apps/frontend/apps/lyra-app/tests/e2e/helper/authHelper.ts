import { expect, type Page } from '@playwright/test';
import { user } from '@lyra/mock-responses';
const authResponses = {
	successful: {
		message: 'OK',
		accessToken:
			'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InNoeWFtLmNoZW4iLCJpYXQiOjE2NzQwOTMxMzQsImV4cCI6MTY3NDA5NDMzNH0.Qd2XARFcMVjdITuAZKISU-zNIxrx1O5sfrFVci7LkQI',
		refreshToken:
			'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1dWlkIjoiMmZlNzdjMzktNDRlMi00NDRhLTliN2QtMmYyOTk2YjYxMjc2IiwiaWF0IjoxNjc0MDkzMTM0LCJleHAiOjE2NzQxMzYzMzR9.67x8vrDc1K2qgtl6M4Kys2fP83_lr4lF8Ohiw5MqmUw',
		otpEnabled: false,
		otpVerified: false,
	},
};
export async function signIn(
	options: { page: Page; startUrl: string } = { page: null, startUrl: '/' }
): Promise<void> {
	const { page, startUrl } = options;
	await page.goto(startUrl);
	await page.goto('/sign-in', { waitUntil: 'networkidle' });
	await expect(page.getByText('Sign in to our platform')).toBeVisible();
	await page.route('**/api/auth/sign-in', (route) =>
		route.fulfill({
			status: 200,
			body: JSON.stringify(authResponses.successful),
		})
	);
	await page.getByTestId('sign-in').click();
	await loadUserData({ page });
}

export async function loadUserData(options: { page: Page }): Promise<void> {
	const { page } = options;
	await page.route('**/api/auth/user', (route) =>
		route.fulfill({
			status: 200,
			body: JSON.stringify(user.admin),
		})
	);
}

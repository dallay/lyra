import { type Page, expect } from '@playwright/test';
import { signIn } from './authHelper';

export async function getSubscribers(page: Page) {
	await page.routeFromHAR('tests/e2e/hars/subscribers.har', {
		url: '**/api/newsletter/subscribers**',
		update: false,
	});
	await page.goto('/audience/subscribers', { waitUntil: 'networkidle' });
}

export type SubscriberCell = {
	name: string;
	email: string;
	status: 'ENABLED' | 'DISABLED' | 'BLOCKLISTED';
};
export async function initialScreenLoad(page: Page) {
	await signIn({ page, startUrl: '/' });
	await getSubscribers(page);
	await page.goto('/dashboard', { waitUntil: 'networkidle' });
	await page.getByRole('navigation').getByText('Audience').click();
	await page.getByRole('navigation').getByRole('link', { name: 'Subscribers' }).click();
	await page.goto('/audience/subscribers', { waitUntil: 'networkidle' });
	await expect(
		page.getByRole('main').locator('div').filter({ hasText: 'Subscribers' })
	).toBeVisible();
}

export async function checkTableColumnContent(page: Page, data: SubscriberCell[]) {
	const allRows = await page.locator('tbody').locator('tr').all();
	for (const rows of allRows) {
		const columns = rows.locator('td');
		const rowValues = await columns.allInnerTexts();
		const [email, name, status] = rowValues;
		const expectedData = data.find((d) => d.email === email);
		const message = `the expected data for ${email} ${name} ${status} is not found`;
		if (!expectedData) console.log(message);
		expect(expectedData).toBeDefined();
		expect(name).toBe(expectedData.name);
		expect(status).toBe(expectedData.status);
	}
}

export async function searchByQueryTerm(page: Page, query: string = 'Henry') {
	await page.getByPlaceholder('Search').fill(query);
	await page.getByTestId('search-subscribers').click();
	const data: SubscriberCell[] = [
		{
			email: 'maimonides@listmonk.app',
			name: 'Maimonides Henry',
			status: 'ENABLED',
		},
		{
			email: 'michel.henry@listmonk.app',
			name: 'Michel Henry',
			status: 'ENABLED',
		},
		{
			email: 'aristotle@listmonk.app',
			name: 'Aristotle Henry',
			status: 'DISABLED',
		},
		{
			email: 'plato@listmonk.app',
			name: 'Plato Henry',
			status: 'ENABLED',
		},
		{
			email: 'averroes@listmonk.app',
			name: 'Averroes Henry',
			status: 'DISABLED',
		},
		{
			email: 'avicenna@listmonk.app',
			name: 'Avicenna Henry',
			status: 'ENABLED',
		},
		{
			email: 'plotinus@listmonk.app',
			name: 'Plotinus Henry',
			status: 'BLOCKLISTED',
		},
		{
			email: 'parmenides@listmonk.app',
			name: 'Parmenides Henry',
			status: 'ENABLED',
		},
		{
			email: 'epicurus@listmonk.app',
			name: 'Epicurus Henry',
			status: 'DISABLED',
		},
		{
			email: 'anselm@listmonk.app',
			name: 'Anselm Henry',
			status: 'DISABLED',
		},
	];
	await checkTableColumnContent(page, data);
}
import { expect, type Page, test } from '@playwright/test';
import { signIn } from './helper/authHelper';
import { getSubscribers } from './helper/susbcriberHelper';

type SubscriberCell = {
	name: string;
	email: string;
	status: 'ENABLED' | 'DISABLED' | 'BLOCKLISTED';
};
async function initialScreenLoad(page: Page) {
	await signIn({ page, startUrl: '/' });
	await getSubscribers({ page, size: 10, cursor: '', search: '' });
	await page.goto('/dashboard', { waitUntil: 'networkidle' });
	await page.getByRole('navigation').getByText('Audience').click();
	await page.getByRole('navigation').getByRole('link', { name: 'Subscribers' }).click();
	await page.goto('/audience/subscribers', { waitUntil: 'networkidle' });
	await expect(
		page.getByRole('main').locator('div').filter({ hasText: 'Subscribers' })
	).toBeVisible();
}

async function checkTableColumnContent(page: Page, data: SubscriberCell[]) {
	const allRows = await page.locator('tbody').locator('tr').all();
	for (const rows of allRows) {
		const columns = rows.locator('td');
		const rowValues = await columns.allInnerTexts();
		const [email, name, status] = rowValues;
		const expectedData = data.find((d) => d.email === email);
		expect(expectedData).toBeDefined();
		expect(name).toBe(expectedData.name);
		expect(status).toBe(expectedData.status);
	}
}
test('pagination subscribers', async ({ page }) => {
	await initialScreenLoad(page);

	// should exist 10 subscribers rows in the table. Search for the first row and the last row to check if the pagination is working
	const data: SubscriberCell[] = [
		{
			email: 'maimonides@test.com',
			name: 'Maimonides',
			status: 'ENABLED',
		},
		{
			email: 'bertrand.russell@test.com',
			name: 'Bertrand Russell',
			status: 'ENABLED',
		},
		{
			email: 'jean-francois.lyotard@test.com',
			name: 'Jean-François Lyotard',
			status: 'DISABLED',
		},
		{
			email: 'ralph.waldo.emerson@test.com',
			name: 'Ralph Waldo Emerson',
			status: 'ENABLED',
		},
		{
			email: 'franz.brentano@test.com',
			name: 'Franz Brentano',
			status: 'BLOCKLISTED',
		},
		{
			email: 'boethius@test.com',
			name: 'Boethius',
			status: 'ENABLED',
		},
		{
			email: 'jean-paul.henry@test.com',
			name: 'Jean-Paul Henry',
			status: 'DISABLED',
		},
		{
			email: 'rene.descartes@test.com',
			name: 'Rene Descartes',
			status: 'ENABLED',
		},
		{
			email: 'emmanuel.henry@test.com',
			name: 'Emmanuel Henry',
			status: 'ENABLED',
		},
		{
			email: 'maurice.henry@test.com',
			name: 'Maurice Henry',
			status: 'ENABLED',
		},
	];
	await checkTableColumnContent(page, data);
	await getSubscribers({
		page,
		size: 10,
		cursor: 'MjAyMi0xMC0yMlQxMjowMDowMC4wMDBa',
	});
	const loadMore = page.getByTestId('load-more');
	await expect(loadMore).toBeVisible();
	await loadMore.click();
	await checkTableColumnContent(page, [
		{
			email: 'maimonides@test.com',
			name: 'Maimonides',
			status: 'ENABLED',
		},
		{
			email: 'michel.henry@test.com',
			name: 'Michel Henry',
			status: 'ENABLED',
		},
	]);
	await expect(loadMore).not.toBeVisible();
});

test('search subscribers', async ({ page }) => {
	await initialScreenLoad(page);

	await getSubscribers({
		page,
		size: 10,
		cursor: 'MjAyMi0xMC0yMlQxMjowMDowMC4wMDBa',
		search: 'Henry',
	});
	await page.getByPlaceholder('Search').fill('Henry');
	await page.getByTestId('search-subscribers').click();
	const data: SubscriberCell[] = [
		{
			name: 'Jean-Paul Henry',
			email: 'jean-paul.henry@test.com',
			status: 'DISABLED',
		},
		{
			name: 'Emmanuel Henry',
			email: 'emmanuel.henry@test.com',
			status: 'ENABLED',
		},
		{
			name: 'Maurice Henry',
			email: 'maurice.henry@test.com',
			status: 'ENABLED',
		},
		{
			name: 'Michel Henry',
			email: 'michel.henry@test.com',
			status: 'ENABLED',
		},
	];
	await checkTableColumnContent(page, data);
});

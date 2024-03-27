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
	await getSubscribers(page);
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
const data: Record<string, SubscriberCell[]> = {
	'batch-1': [
		{
			email: 'boethius@listmonk.app',
			name: 'Boethius Henry',
			status: 'ENABLED',
		},

		{
			email: 'ralph.waldo.emerson@listmonk.app',
			name: 'Ralph Waldo Emerson',
			status: 'ENABLED',
		},
		{
			email: 'bertrand.russell@listmonk.app',
			name: 'Bertrand Russell',
			status: 'ENABLED',
		},
		{
			email: 'jean-paul.sartre@listmonk.app',
			name: 'Jean-Paul Sartre',
			status: 'DISABLED',
		},
		{
			email: 'rene.descartes@listmonk.app',
			name: 'Rene Descartes',
			status: 'ENABLED',
		},
		{
			email: 'jean-francois.lyotard@listmonk.app',
			name: 'Jean-François Lyotard',
			status: 'ENABLED',
		},
		{
			email: 'emmanuel.levinas@listmonk.app',
			name: 'Emmanuel Levinas',
			status: 'ENABLED',
		},
		{
			email: 'bonaventure@listmonk.app',
			name: 'Bonaventure Henry',
			status: 'DISABLED',
		},
		{
			email: 'franz.brentano@listmonk.app',
			name: 'Franz Brentano',
			status: 'ENABLED',
		},

		{
			email: 'maurice.merleau-ponty@listmonk.app',
			name: 'Maurice Merleau-Ponty',
			status: 'ENABLED',
		},
	],
	'batch-2': [
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
			email: 'john@example.com',
			name: 'John Doe',
			status: 'ENABLED',
		},
		{
			email: 'johann.gottlieb.fichte@listmonk.app',
			name: 'Johann Gottlieb Fichte',
			status: 'ENABLED',
		},
		{
			email: 'theodor.w.adorno@listmonk.app',
			name: 'Theodor W. Adorno',
			status: 'ENABLED',
		},
		{
			email: 'ernst.cassirer@listmonk.app',
			name: 'Ernst Cassirer',
			status: 'ENABLED',
		},
		{
			email: 'john.stuart.mill@listmonk.app',
			name: 'John Stuart Mill',
			status: 'ENABLED',
		},
		{
			email: 'walter.benjamin@listmonk.app',
			name: 'Walter Benjamin',
			status: 'ENABLED',
		},
		{
			email: 'ylaz@gft.com',
			name: 'Yuniel Acosta',
			status: 'ENABLED',
		},
		{
			email: 'gilles.deleuze@listmonk.app',
			name: 'Gilles Deleuze',
			status: 'ENABLED',
		},
	],
};
test('pagination subscribers', async ({ page }) => {
	await initialScreenLoad(page);

	await checkTableColumnContent(page, data['batch-1']);
	await getSubscribers(page);
	const loadMore = page.getByTestId('load-more');
	await expect(loadMore).toBeVisible();
	await loadMore.click();
	await checkTableColumnContent(page, data['batch-2']);
	for (let i = 0; i < 10; i++) {
		await loadMore.click();
	}
	await expect(loadMore).not.toBeVisible();
});

test('search subscribers', async ({ page }) => {
	await initialScreenLoad(page);

	await getSubscribers(page);
	await page.getByPlaceholder('Search').fill('Henry');
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
});

test('filter subscribers', async ({ page }) => {
	await initialScreenLoad(page);
	await getSubscribers(page);
	const showFiltersButton = page.getByTestId('show-filters');
	await showFiltersButton.click();
	const addFilterButton = page.getByTestId('add-filter');
	await addFilterButton.click();
	const filterStatus = page.getByTestId('filter-status');
	await filterStatus.click();
	const filterSelect = page.getByTestId('filter-select').nth(1);
	await expect(filterSelect).toBeVisible();
	await filterSelect.click();
	await page.getByText('DISABLED').click();
	await page.getByTestId('apply-filter').click();

	await checkTableColumnContent(page, [
		{
			email: 'aristotle@listmonk.app',
			name: 'Aristotle Henry',
			status: 'DISABLED',
		},
		{
			email: 'averroes@listmonk.app',
			name: 'Averroes Henry',
			status: 'DISABLED',
		},
		{
			email: 'admin@test.com',
			name: 'Admin Admin',
			status: 'DISABLED',
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
		{
			email: 'lucretius@listmonk.app',
			name: 'Lucretius Henry',
			status: 'DISABLED',
		},
		{
			email: 'proclus@listmonk.app',
			name: 'Proclus Henry',
			status: 'DISABLED',
		},
	]);
});

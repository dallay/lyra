import { test } from '@playwright/test';
import {
	checkTableColumnContent,
	getSubscribers,
	initialScreenLoad,
	searchByQueryTerm,
} from '../helper/susbcriberHelper';
test('search and filter subscribers', async ({ page }) => {
	await initialScreenLoad(page);
	await getSubscribers(page);
	await searchByQueryTerm(page);
	await page.getByTestId('show-filters').click();
	await page.getByTestId('add-filter').click();
	await page.getByTestId('filter-status').click();
	await page.getByTestId('filter-select').nth(1).click();
	await page.getByText('DISABLED').first().click();
	await page.getByTestId('apply-filter').click();
	await checkTableColumnContent(page, [
		{
			email: 'bonaventure@listmonk.app',
			name: 'Bonaventure Henry',
			status: 'DISABLED',
		},
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

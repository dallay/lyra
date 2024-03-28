import { expect, test } from '@playwright/test';
import {
	checkTableColumnContent,
	getSubscribers,
	initialScreenLoad,
} from '../helper/susbcriberHelper';

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

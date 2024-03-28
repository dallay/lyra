import { expect, test } from '@playwright/test';
import {
	checkTableColumnContent,
	getSubscribers,
	getSubscribersByDateRange,
	initialScreenLoad,
} from '../helper/susbcriberHelper';
import { format, subDays } from 'date-fns';

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

test('filter by date range subscribers', async ({ page }) => {
	await initialScreenLoad(page);
	const dayDelay = 1;
	const today = new Date();
	today.setHours(23, 0, 0, 0);
	const formatStr = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	const startDate = format(subDays(today, 7 + dayDelay), formatStr);
	const endDate = format(subDays(today, 1 + dayDelay), formatStr);
	await getSubscribersByDateRange(page, startDate, endDate);
	const showFiltersButton = page.getByTestId('show-filters');
	await showFiltersButton.click();
	const addFilterButton = page.getByTestId('add-filter');
	await addFilterButton.click();
	await page
		.locator('label')
		.filter({ hasText: 'Use Date Range Filter' })
		.locator('div')
		.nth(1)
		.click();
	await page.getByTestId('historical-date-range-picker').nth(1).click();
	await page.getByTestId('7D').click();
	await page.getByTestId('search-subscribers').click();
	await checkTableColumnContent(page, [
		{
			email: 'john.doe@test.com',
			name: 'John Doe',
			status: 'ENABLED',
		},
	]);
});

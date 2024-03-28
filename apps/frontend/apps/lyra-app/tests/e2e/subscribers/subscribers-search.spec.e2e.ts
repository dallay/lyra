import { test } from '@playwright/test';
import { getSubscribers, initialScreenLoad, searchByQueryTerm } from '../helper/susbcriberHelper';

test('search subscribers', async ({ page }) => {
	await initialScreenLoad(page);

	await getSubscribers(page);
	await searchByQueryTerm(page);
});

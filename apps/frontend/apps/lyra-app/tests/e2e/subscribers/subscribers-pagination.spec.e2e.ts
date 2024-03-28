import { expect, test } from '@playwright/test';
import {
	checkTableColumnContent,
	getSubscribers,
	initialScreenLoad,
	SubscriberCell,
} from '../helper/susbcriberHelper';

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

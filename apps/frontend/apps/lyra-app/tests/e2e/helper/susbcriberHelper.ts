import { type Page } from '@playwright/test';
import { Buffer } from 'buffer';
import {
	PageResponse,
	Subscriber,
} from '../../../src/routes/(backstage)/audience/subscribers/types';
import { chunk } from '@lyra/utilities';

export const allSubscribers: Subscriber[] = [
	{
		id: 'bbf25966-6f2d-4cc5-a6da-19e79dbaba1e',
		email: 'boethius@test.com',
		name: 'Boethius',
		status: 'ENABLED',
		createdAt: '2021-10-18T12:00:00.000Z',
		updatedAt: '2021-10-18T12:00:00.000Z',
	},
	{
		id: 'b8f2317a-686f-4ace-a218-5c98a05cb88a',
		email: 'ralph.waldo.emerson@test.com',
		name: 'Ralph Waldo Emerson',
		status: 'ENABLED',
		createdAt: '2021-10-13T12:00:00.000Z',
		updatedAt: '2021-10-13T12:00:00.000Z',
	},
	{
		id: '15a3f1bf-6236-40bd-83d4-69a9539ab1ea',
		email: 'jean-paul.henry@test.com',
		name: 'Jean-Paul Henry',
		status: 'DISABLED',
		createdAt: '2021-10-21T12:00:00.000Z',
		updatedAt: '2021-10-21T12:00:00.000Z',
	},
	{
		id: 'e3839fed-f18f-4461-9e86-5bf2d5868ead',
		email: 'bertrand.russell@test.com',
		name: 'Bertrand Russell',
		status: 'ENABLED',
		createdAt: '2020-10-18T12:00:00.000Z',
		updatedAt: '2020-10-18T12:00:00.000Z',
	},
	{
		id: 'b88f202b-964d-4f44-b080-3aaa6ef03052',
		email: 'rene.descartes@test.com',
		name: 'Rene Descartes',
		status: 'ENABLED',
		createdAt: '2021-11-18T12:00:00.000Z',
		updatedAt: '2021-11-18T12:00:00.000Z',
	},
	{
		id: '5616e0ed-2305-4d6f-88f2-9ef3d6d72a69',
		email: 'jean-francois.lyotard@test.com',
		name: 'Jean-François Lyotard',
		status: 'DISABLED',
		createdAt: '2021-10-10T12:00:00.000Z',
		updatedAt: '2021-10-10T12:00:00.000Z',
	},
	{
		id: 'dd3ff9d4-aee0-4a89-930f-bdd020845f92',
		email: 'emmanuel.henry@test.com',
		name: 'Emmanuel Henry',
		status: 'ENABLED',
		createdAt: '2021-12-12T12:00:00.000Z',
		updatedAt: '2021-12-12T12:00:00.000Z',
	},
	{
		id: '7b868e1e-d2d1-4b8a-9949-59bc4b775d0f',
		email: 'bonaventure@test.com',
		name: 'Bonaventure',
		status: 'ENABLED',
		createdAt: '2022-10-22T12:00:00.000Z',
		updatedAt: '2022-10-22T12:00:00.000Z',
	},
	{
		id: '30b14a48-ddf4-413d-a638-1d26beb52ae2',
		email: 'franz.brentano@test.com',
		name: 'Franz Brentano',
		status: 'BLOCKLISTED',
		createdAt: '2021-10-17T12:00:00.000Z',
		updatedAt: '2021-10-17T12:00:00.000Z',
	},
	{
		id: 'a72d3adb-7f07-4837-b592-0be854d20a67',
		email: 'maurice.henry@test.com',
		name: 'Maurice Henry',
		status: 'ENABLED',
		createdAt: '2021-12-18T12:00:00.000Z',
		updatedAt: '2021-12-18T12:00:00.000Z',
	},
	{
		id: '96bd3f93-7be8-4ac6-9e68-1d2a50650cc6',
		email: 'maimonides@test.com',
		name: 'Maimonides',
		status: 'ENABLED',
		createdAt: '2019-10-18T12:00:00.000Z',
		updatedAt: '2019-10-18T12:00:00.000Z',
	},
	{
		id: 'a4053f51-ddee-4abc-bf5d-767d7588b711',
		email: 'michel.henry@test.com',
		name: 'Michel Henry',
		status: 'ENABLED',
		createdAt: '2022-12-15T12:00:00.000Z',
		updatedAt: '2022-12-16T12:00:00.000Z',
	},
];

type SubscriberRequestParameters = {
	size?: number;
	cursor?: string;
	search?: string;
};

function searchAndFilter(subscribers: Subscriber[], search: string) {
	return subscribers.filter(
		(subscriber) =>
			subscriber.name.includes(search) ||
			subscriber.email.includes(search) ||
			subscriber.status.includes(search)
	);
}

function getSubscriberResponse({
	size = 10,
	cursor = '',
	search = '',
}: SubscriberRequestParameters = {}): PageResponse<Subscriber> {
	// filter subscribers by search term
	let filteredSubscribers = allSubscribers;
	if (search && search !== '') {
		filteredSubscribers = searchAndFilter(allSubscribers, search);
	}

	// If cursor is provided, filter subscribers whose createdAt is greater than cursor
	if (cursor && cursor !== '') {
		const decodedCursor = Buffer.from(cursor, 'base64').toString();
		filteredSubscribers = filteredSubscribers.filter(
			(subscriber) => new Date(subscriber.createdAt) > new Date(decodedCursor)
		);
	}

	// Order subscribers by createdAt
	filteredSubscribers.sort(
		(a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
	);

	// split subscribers into chunks of size `size`
	const chunks = chunk(filteredSubscribers, size);

	// get the subscribers for the current page
	const subscribers = chunks[0] || [];

	// calculate the next cursor based on the last subscriber in the chunk
	let nextCursor = null;
	if (chunks.length > 1) {
		const nextCursorSubscriber = chunks[1][0];
		nextCursor = Buffer.from(nextCursorSubscriber.createdAt).toString('base64');
	}

	return {
		data: subscribers,
		nextPageCursor: nextCursor,
	};
}

export async function getSubscribers(
	options: { page: Page; size?: number; cursor?: string; search?: string } = {
		page: null,
		size: 10,
		cursor: '',
		search: '',
	}
) {
	const { page, size, search, cursor } = options;
	const params: Record<string, string> = {};
	if (search && search !== '') {
		params.search = search;
	}
	if (size) {
		params.size = size.toString();
	}
	if (cursor && cursor !== '') {
		params.cursor = cursor;
	}

	const url = `**/api/newsletter/subscribers?${decodeURIComponent(new URLSearchParams(params).toString())}`;
	await page.route(url, async (route) => {
		const subscriberResponse = getSubscriberResponse({ size, cursor: cursor, search });
		await route.fulfill({
			status: 200,
			body: JSON.stringify(subscriberResponse),
		});
	});
	await page.goto('/audience/subscribers', { waitUntil: 'networkidle' });
}

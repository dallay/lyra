import type { APIRoute } from 'astro';

const BACKEND_URL = import.meta.env.BACKEND_URL ?? process.env.BACKEND_URL;
const getProxyUrl = (request: Request) => {
	const proxyUrl = new URL(BACKEND_URL ?? 'http://localhost:8080');
	const requestUrl = new URL(request.url);
	return new URL(requestUrl.pathname, proxyUrl);
};

export const ALL: APIRoute = async ({ request }) => {
	const proxyUrl = getProxyUrl(request);
	const response = await fetch(proxyUrl.href, request);
	return new Response(response.body);
};

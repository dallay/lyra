import FetchFactory, { XSRF_TOKEN_COOKIE } from '../factory';
import Routes from '../routes.client';
import { useCookie } from '#imports';

class CommonModule extends FetchFactory {
	private readonly RESOURCE = Routes.Common;
	async healthCheck() {
		return this.call<string>({
			method: 'GET',
			url: `${this.RESOURCE.HealthCheck()}`,
		});
	}
	async csrfToken() {
		// check if the csrf token is already in the cookie. If exists do not fetch it again from the server
		const xsrfToken = useCookie(XSRF_TOKEN_COOKIE).value;
		if (xsrfToken) {
			return xsrfToken;
		}
		try {
			const response = await this.call<string>({
				method: 'GET',
				url: `${this.RESOURCE.HealthCheck()}`,
			});
			if (response !== 'OK') {
				console.error('Failed to fetch CSRF token');
				return '';
			}
		} catch (err) {
			console.error('Error fetching CSRF token:', err);
		}

		return useCookie(XSRF_TOKEN_COOKIE).value || '';
	}
}

export default CommonModule;

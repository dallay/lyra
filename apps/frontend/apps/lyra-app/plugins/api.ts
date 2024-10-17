import { defineNuxtPlugin, useCookie, useRuntimeConfig } from '#imports';
import type { FetchResponse, ResponseType } from 'ofetch';
import FormModule from '~/repository/modules/form.module';
import AuthModule from '~/repository/modules/auth.module';
import CommonModule from '~/repository/modules/common.module';
import type { AccessToken } from '@/domain/authentication';
import OrganizationModule from '~/repository/modules/organization.module';
import TeamModule from '~/repository/modules/team.module';
import TeamMemberModule from '~/repository/modules/team-member.module';
import SecureFetchFactory from '~/repository/secure.factory';
import type FetchFactory from '~/repository/factory';
import SubscriberModule from '~/repository/modules/subscriber.module';
import TagModule from '~/repository/modules/tag.module';
import LinkPreviewModule from '~/repository/modules/link-preview.module';

/**
 * Interface representing the API instance with different modules.
 */
export interface IApiInstance {
  form: FormModule;
  auth: AuthModule;
  common: CommonModule;
  organization: OrganizationModule;
  team: TeamModule;
  teamMember: TeamMemberModule;
  subscriber: SubscriberModule;
  tag: TagModule;
  linkPreview: LinkPreviewModule;
}

/**
 * Utility function to create a request with updated headers.
 * @param {Request | string} request - The original request or URL string.
 * @param {Record<string, string>} headers - The headers to be added or updated.
 * @returns {Request} - The updated request with new headers.
 */
function createRequestWithHeaders(
  request: Request | string,
  headers: Record<string, string>,
): Request {
  if (typeof request === 'string') {
    return new Request(request, { headers });
  }
  Object.assign(request.headers, headers);
  return request;
}

/**
 * Utility function to retry a request with a new access token.
 * @param {Request | string} request - The original request or URL string.
 * @param {AccessToken} newAccessToken - The new access token.
 * @returns {Promise<FetchResponse<any> & FetchResponse<ResponseType>>} - The response of the retried request.
 */

async function retryRequest(
  request: Request | string,
  newAccessToken: AccessToken,
): Promise<FetchResponse<unknown> & FetchResponse<ResponseType>> {
  const headers = { Authorization: `Bearer ${newAccessToken}` };
  const updatedRequest = createRequestWithHeaders(request, headers);
  // biome-ignore lint/correctness/noUndeclaredVariables: fetch is a global function in nuxt
  return $fetch.raw(updatedRequest);
}

/**
 * Type guard to verify if a module is an instance of SecureFetchFactory.
 */
function isSecureFetchFactory(module: FetchFactory): module is SecureFetchFactory {
  return module instanceof SecureFetchFactory;
}

/**
 * Utility function to update the access token in all API modules.
 * @param {IApiInstance} modules - The API modules to be updated.
 * @param {AccessToken} accessToken - The new access token.
 */
function updateModulesAccessToken(modules: IApiInstance, accessToken: AccessToken): void {
  if (accessToken?.token) {
    for (const moduleName in modules) {
      const module = modules[moduleName as keyof IApiInstance];
      if (isSecureFetchFactory(module)) {
        module.setAccessToken(accessToken.token);
      }
    }
  }
}

/**
 * Nuxt plugin to set up API modules and handle request/response logic.
 */
export default defineNuxtPlugin(async (_) => {
  const config = useRuntimeConfig();
  const baseUrlProxyServer: string = config.public.backendUrl;

  // biome-ignore lint/correctness/noUndeclaredVariables: $fetch is a global function in nuxt
  const apiFetcher = $fetch.create({
    baseURL: baseUrlProxyServer,
    // biome-ignore lint/correctness/noUnusedVariables: Biome needs support for Nuxt.
    onRequest({ request, response }) {
      const language = useCookie('language').value;
      if (language) {
        const headers = { 'Accept-Language': language };
        request = createRequestWithHeaders(request, headers);
      }
    },
    onResponse: async ({ request, response }) => {
      if (response.status === 401) {
        try {
          const newAccessToken = await authModule.refreshToken();
          if (newAccessToken) {
            response = await retryRequest(request, newAccessToken);
          }
        } catch (err) {
          console.error('Error refreshing token:', err);
        }
      }
    },
  });

  const formModule = new FormModule(apiFetcher);
  const authModule = new AuthModule(apiFetcher);
  const commonModule = new CommonModule(apiFetcher);
  const organizationModule = new OrganizationModule(apiFetcher);
  const teamModule = new TeamModule(apiFetcher);
  const teamMemberModule = new TeamMemberModule(apiFetcher);
  const subscriberModule = new SubscriberModule(apiFetcher);
  const tagModule = new TagModule(apiFetcher);
  const linkPreviewModule = new LinkPreviewModule(apiFetcher);

  const modules: IApiInstance = {
    form: formModule,
    auth: authModule,
    common: commonModule,
    organization: organizationModule,
    team: teamModule,
    teamMember: teamMemberModule,
    subscriber: subscriberModule,
    tag: tagModule,
    linkPreview: linkPreviewModule,
  };

  if (import.meta.client) {
    try {
      await modules.common.csrfToken();
      const data = await modules.auth.refreshToken();
      updateModulesAccessToken(modules, data);
    } catch (err) {
      console.error('Error occurred during client initialization:', err);
    }
  }

  return {
    provide: {
      api: modules,
      updateModulesAccessToken,
    },
  };
});

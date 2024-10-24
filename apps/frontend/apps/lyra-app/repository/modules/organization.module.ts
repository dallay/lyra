import Routes from '../routes.client';
import type { IOrganization } from '@/domain/organization';
import type { DataResponse } from '@lyra/shared';
import SecureFetchFactory from '~/repository/secure.factory';

class OrganizationModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Organization;
  async fetchAll() {
    return this.call<DataResponse<IOrganization>>({
      method: 'GET',
      url: `${this.RESOURCE.FetchAll()}`,
      fetchOptions: {
        headers: {
          ...(this.accessToken
            ? {
                Authorization: `Bearer ${this.accessToken}`,
              }
            : {}),
        },
      },
    });
  }
}

export default OrganizationModule;

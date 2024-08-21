import FetchFactory from "../factory";
import Routes from "../routes.client";
import type {IOrganization} from "@lyra/domain";
import type {ResponseData} from "@lyra/shared";
import SecureFetchFactory from "~/repository/secure.factory";

class OrganizationModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Organization;
  // private accessToken = '';

  async fetchAll() {
    return this.call<ResponseData<IOrganization>>(
      {
        method: 'GET', url: `${this.RESOURCE.FetchAll()}`, fetchOptions: {
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {})
          },
        }
      }
    )
  }

  // override setAccessToken(accessToken: string) {
  //   this.accessToken = accessToken;
  // }
}

export default OrganizationModule;

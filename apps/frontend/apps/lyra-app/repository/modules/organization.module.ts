import FetchFactory from "../factory";
import Routes from "../routes.client";
import type {FormResponse, FormId} from "@lyra/api-services";

class OrganizationModule extends FetchFactory {
  private readonly RESOURCE = Routes.Form;
  private accessToken = '';

  async fetchAll() {
    return this.call<FormResponse>(
      {
        method: 'GET', url: `${this.RESOURCE.FetchAll()}`
      }
    )
  }

  async createForm(dto : FormResponse) {
    return this.call<FormResponse>(
      {
        method: 'POST', url: `${this.RESOURCE.CreateForm()}`,body:dto, fetchOptions: {
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {})
          },

        }
      }
    )
  }
  async fetchDetail(id : FormId) {
    return this.call<FormResponse>(
      {
        method: 'GET', url: `${this.RESOURCE.FetchDetail(id)}`, fetchOptions: {
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {})
          },

        }
      }
    )
  }

  setAccessToken(accessToken: string) {
    this.accessToken = accessToken;
  }
}

export default OrganizationModule;

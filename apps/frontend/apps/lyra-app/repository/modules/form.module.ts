import Routes from "../routes.client";
import type {
   CreateFormRequest,
   FormId,
   FormResponse,
   UpdateFormRequest
} from "@lyra/domain";
import type {ResponseData} from "@lyra/shared";
import SecureFetchFactory from "~/repository/secure.factory";
import {ACCEPT_HEADER} from "~/repository/factory";

class FormModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Form;

  async fetchAll() {
    return this.call<ResponseData<FormResponse>>(
      {
        method: 'GET', url: `${this.RESOURCE.FetchAll()}`,
        fetchOptions:{
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {})
          },
        }
      }
    )
  }

  async createForm(id: FormId, dto : CreateFormRequest) {
    return this.call<FormResponse>(
      {
        method: 'PUT', url: `${this.RESOURCE.CreateForm(id)}`,body:dto, fetchOptions: {
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {}),
            Accept: ACCEPT_HEADER,
          },
        }
      }
    )
  }

  async updateForm(id: FormId, dto : UpdateFormRequest) {
    return this.call<FormResponse>(
      {
        method: 'PUT', url: `${this.RESOURCE.UpdateForm(id)}`,body:dto, fetchOptions: {
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {}),
            Accept: ACCEPT_HEADER,
          },
        }
      }
    )
  }

  async deleteForm(id: FormId) {
    return this.call({
      method: 'DELETE', url: `${this.RESOURCE.DeleteForm(id)}`, fetchOptions: {
        headers: {
          ...(this.accessToken ? {
            'Authorization': `Bearer ${this.accessToken}`
          } : {}),
          Accept: ACCEPT_HEADER,
        },
      }
    })
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
}

export default FormModule;

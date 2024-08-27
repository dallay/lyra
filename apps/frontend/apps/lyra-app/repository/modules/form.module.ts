import Routes from "../routes.client";
import type {
   CreateFormRequest,
   FormId,
   FormResponse, OrganizationId,
   UpdateFormRequest
} from "@lyra/domain";
import type {ResponseData} from "@lyra/shared";
import SecureFetchFactory from "~/repository/secure.factory";
import {ACCEPT_HEADER} from "~/repository/factory";

class FormModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Form;

  async fetchAll(organizationId: OrganizationId) {
    return this.call<ResponseData<FormResponse>>(
      {
        method: 'GET', url: `${this.RESOURCE.FetchAll(organizationId)}`,
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

  async createForm(organizationId: OrganizationId,formId: FormId, dto : CreateFormRequest) {
    return this.call<FormResponse>(
      {
        method: 'PUT', url: `${this.RESOURCE.CreateForm(organizationId, formId)}`,body:dto, fetchOptions: {
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

  async updateForm(organizationId: OrganizationId,formId: FormId, dto : UpdateFormRequest) {
    return this.call<FormResponse>(
      {
        method: 'PUT', url: `${this.RESOURCE.UpdateForm(organizationId, formId)}`,body:dto, fetchOptions: {
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

  async deleteForm(organizationId: OrganizationId,formId: FormId) {
    return this.call({
      method: 'DELETE', url: `${this.RESOURCE.DeleteForm(organizationId,formId)}`, fetchOptions: {
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

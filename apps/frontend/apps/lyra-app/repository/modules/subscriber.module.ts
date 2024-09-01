import Routes from "../routes.client";
import type {
  CriteriaParam,
  OrganizationId,
  QuerySort, Subscriber, PageResponse,
  SubscriberId,
  SubscriberRequest
} from "@lyra/domain";
import  {buildParams} from "@lyra/domain";
import SecureFetchFactory from "~/repository/secure.factory";

class SubscriberModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Subscriber;

  async createSubscriber(organizationId: OrganizationId, subscribeId: SubscriberId,
                         request: SubscriberRequest) {
    return this.call(
      {
        method: 'PUT',
        url: `${this.RESOURCE.CreateSubscriber(organizationId, subscribeId)}`,
        body: request
      }
    )
  }

  async fetchAll(organizationId: OrganizationId, criteria?: CriteriaParam,
                 sort?: QuerySort, size = 10, cursor?: string) {
    const headers = await this.buildHeaders()
    return this.call<PageResponse<Subscriber>>(
      {
        method: 'GET',
        url: `${this.RESOURCE.FetchAll(organizationId)}`,
        fetchOptions: {
          params: buildParams(criteria, sort, size, cursor),
          headers: {
            ...headers,
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {})
          }
        }
      }
    )
  }
}

export default SubscriberModule;

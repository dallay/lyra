import Routes from "../routes.client";

import SecureFetchFactory from "~/repository/secure.factory";
import type {PageResponse} from "@lyra/shared";
import {OrganizationId} from "~/domain/organization";
import {type Subscriber, SubscriberId, type SubscriberRequest} from "~/domain/subscriber";
import {type CriteriaQueryParams, toQueryParams} from "~/domain/criteria";

class SubscriberModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Subscriber;

  async createSubscriber(
    organizationId: OrganizationId,
    subscribeId: SubscriberId,
    request: SubscriberRequest,
  ) {
    return this.call<void>({
      method: 'PUT',
      url: `${this.RESOURCE.CreateSubscriber(organizationId, subscribeId)}`,
      body: request,
    });
  }

  async fetchAll(
    organizationId: OrganizationId,
    criteria: CriteriaQueryParams
  ) {
    console.log('[CRITERIA]', criteria)
    const headers = await this.buildHeaders();
    const params = toQueryParams(criteria)
    console.log('[PARAMS]', params)
    return this.call<PageResponse<Subscriber>>({
      method: 'GET',
      url: `${this.RESOURCE.FetchAll(organizationId)}`,
      fetchOptions: {
        params: params,
        headers: {
          ...headers,
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

export default SubscriberModule;

import Routes from "../routes.client";

import SecureFetchFactory from "~/repository/secure.factory";
import type {PageResponse} from "@lyra/shared";
import {OrganizationId} from "~/domain/organization";
import {
  type Subscriber,
  type SubscriberCountByStatusResponse,
  type SubscriberCountByTagsResponse,
  SubscriberId,
  type SubscriberRequest
} from "~/domain/subscriber";
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
    const headers = await this.buildHeaders();
    const params = toQueryParams(criteria)
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

  async countByStatus(organizationId: OrganizationId) {
    const headers = await this.buildHeaders();
    return this.call<SubscriberCountByStatusResponse>({
      method: 'GET',
      url: `${this.RESOURCE.CountByStatus(organizationId)}`,
      fetchOptions: {
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

  async countByTags(organizationId: OrganizationId) {
    const headers = await this.buildHeaders();
    return this.call<SubscriberCountByTagsResponse>({
      method: 'GET',
      url: `${this.RESOURCE.CountByTags(organizationId)}`,
      fetchOptions: {
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

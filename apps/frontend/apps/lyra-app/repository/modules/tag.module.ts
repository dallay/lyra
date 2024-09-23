import Routes from "../routes.client";

import SecureFetchFactory from "~/repository/secure.factory";
import type {DataResponse} from "@lyra/shared";
import {OrganizationId} from "~/domain/organization";
import type TagId from "~/domain/tag/TagId";
import type {TagRequest} from "~/domain/tag";
import type { TagResponse } from "~/domain/tag/TagResponse";

class TagModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Tag;

  async createTag(
    organizationId: OrganizationId,
    tagId: TagId,
    request: TagRequest,
  ) {
    const headers = await this.buildHeaders();
    return this.call<void>({
      method: 'PUT',
      url: `${this.RESOURCE.CreateTag(organizationId, tagId)}`,
      body: request,
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

  async fetchAllTags(organizationId: OrganizationId) : Promise<DataResponse<TagResponse>> {
    const headers = await this.buildHeaders();
    return this.call<DataResponse<TagResponse>>({
      method: 'GET',
      url: `${this.RESOURCE.FetchAll(organizationId)}`,
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

export default TagModule;

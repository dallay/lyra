import Routes from "../routes.client";
import type {OrganizationId, SubscriberId, SubscriberRequest} from "@lyra/domain";
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
}

export default SubscriberModule;

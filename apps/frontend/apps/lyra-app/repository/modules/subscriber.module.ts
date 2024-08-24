import Routes from "../routes.client";
import type {SubscriberId, SubscriberRequest, Subscriber} from "@lyra/domain";
import SecureFetchFactory from "~/repository/secure.factory";

class SubscriberModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Subscriber;

  async createSubscriber(id: SubscriberId,request : SubscriberRequest) {
    return this.call<Subscriber>(
      {
        method: 'PUT', url: `${this.RESOURCE.CreateSubscriber(id)}`,body:request, fetchOptions: {
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

export default SubscriberModule;

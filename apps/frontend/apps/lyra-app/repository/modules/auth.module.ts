import FetchFactory from "../factory";
import Routes from "../routes.client";
import type {AccessToken} from "@lyra/api-services";

class AuthModule extends FetchFactory {
  private readonly RESOURCE = Routes.User;
  async authenticate(identifier: string, password: string) {
    console.log('🧪🧪🧪🧪 AuthModule.authenticate:', identifier, password);
    return this.call<AccessToken>(
      {
        method: 'POST', url: `${this.RESOURCE.Authenticate()}`, body: {username:identifier, password}
      }
    )
  }
  async refreshToken() {
    return this.call<AccessToken>(
      {
        method: 'POST', url: `${this.RESOURCE.RefreshToken()}`
      }
    )
  }
}

export default AuthModule;

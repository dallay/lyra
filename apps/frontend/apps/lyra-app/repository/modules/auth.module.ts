import FetchFactory from "../factory";
import Routes from "../routes.client";
import type {AccessToken} from "@lyra/domain";

class AuthModule extends FetchFactory {
  private readonly RESOURCE = Routes.Auth;
  async authenticate(identifier: string, password: string) {
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
  async logout() {
    return this.call<AccessToken>(
      {
        method: 'POST', url: `${this.RESOURCE.Logout()}`
      }
    )
  }
}

export default AuthModule;

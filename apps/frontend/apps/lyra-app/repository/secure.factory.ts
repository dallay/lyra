import FetchFactory from "~/repository/factory";

export default class SecureFetchFactory extends FetchFactory {
  protected accessToken = '';

  setAccessToken(accessToken: string) {
    this.accessToken = accessToken;
  }
}

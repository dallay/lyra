import FetchFactory from "../factory";
import Routes from "../routes.client";
import type {ResponseData} from "@lyra/shared";
import type {OrganizationTeamMember} from "@lyra/domain";
import SecureFetchFactory from "~/repository/secure.factory";

class TeamMemberModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.TeamMember;
  // private accessToken = '';

  async fetchAll() {
    return this.call<ResponseData<OrganizationTeamMember>>(
      {
        method: 'GET', url: `${this.RESOURCE.FetchAll()}`, fetchOptions: {
          headers: {
            ...(this.accessToken ? {
              'Authorization': `Bearer ${this.accessToken}`
            } : {})
          },
        }
      }
    )
  }

  // setAccessToken(accessToken: string) {
  //   this.accessToken = accessToken;
  // }
}

export default TeamMemberModule;

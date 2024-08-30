import Routes from "../routes.client";
import type {ResponseData} from "@lyra/shared";
import type {OrganizationTeamMember} from "@lyra/domain";
import SecureFetchFactory from "~/repository/secure.factory";

class TeamMemberModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.TeamMember;

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
}

export default TeamMemberModule;

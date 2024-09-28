import Routes from '../routes.client';
import type { ResponseData } from '@lyra/shared';
import SecureFetchFactory from '~/repository/secure.factory';
import { OrganizationId } from '~/domain/organization';
import { type ITeam, TeamId } from '~/domain/team';

export interface CreateTeamRequest {
  organizationId: OrganizationId;
  teamId: TeamId;
  name: string;
}

class TeamModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Team;

  async fetchAll(id: OrganizationId) {
    return this.call<ResponseData<ITeam>>({
      method: 'GET',
      url: `${this.RESOURCE.FetchAll(id)}`,
      fetchOptions: {
        headers: {
          ...(this.accessToken
            ? {
                Authorization: `Bearer ${this.accessToken}`,
              }
            : {}),
        },
      },
    });
  }

  async createTeam({ organizationId, teamId, name }: CreateTeamRequest) {
    return this.call({
      method: 'PUT',
      url: `${this.RESOURCE.CreateTeam(teamId)}`,
      body: { organizationId: organizationId.value, name },
      fetchOptions: {
        headers: {
          Accept: 'application/vnd.api.v1+json',
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

export default TeamModule;

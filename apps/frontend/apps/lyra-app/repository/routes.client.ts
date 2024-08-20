
/*
* Prefix /api for api server
*/
import type {FormId, OrganizationId, UserId, TeamId} from "@lyra/domain";

const prefix : string = '/api';

/*
* Prefix /api/another for another server
* */
// const anotherPrefix : string = '/api/another'

const Routes = {
  Form:{
    FetchAll:()  => `${prefix}/forms`,
    FetchDetail : (id: FormId) => `${prefix}/forms/${id.value}`,
    CreateForm :()  => `${prefix}/forms`
  },
  Auth:{
    Authenticate:()  => `${prefix}/login`,
    RefreshToken:()  => `${prefix}/refresh-token`,
    Logout:()  => `${prefix}/logout`
  },
  Common:{
    HealthCheck:()  => `${prefix}/health-check`
  },
  Organization: {
    FetchAll:()  => `${prefix}/organization`,
  },
  Team: {
    FetchAll:(id: OrganizationId)  => `${prefix}/organization/${id.value}/team`,
    CreateTeam:(id: TeamId)  => `${prefix}/team/${id.value}`,
  },
  TeamMember: {
    FetchAll:()  => `${prefix}/team/member`,
  }
}

export default Routes

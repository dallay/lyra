
/*
* Prefix /api for api server
*/
import type {FormId, OrganizationId, SubscriberId, TeamId} from "@lyra/domain";

const prefix : string = '/api';

/*
* Prefix /api/another for another server
* */
// const anotherPrefix : string = '/api/another'

const Routes = {
  Auth:{
    Authenticate:()  => `${prefix}/login`,
    RefreshToken:()  => `${prefix}/refresh-token`,
    Logout:()  => `${prefix}/logout`
  },
  Subscriber: {
    CreateSubscriber: (id: SubscriberId) => `${prefix}/newsletter/subscribers/${id.value}`,
  },
  Form:{
    FetchAll:()  => `${prefix}/forms`,
    FetchDetail : (id: FormId) => `${prefix}/forms/${id.value}`,
    CreateForm :()  => `${prefix}/forms`
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

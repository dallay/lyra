
/*
* Prefix /api for api server
*/
import type {FormId, OrganizationId, SubscriberId, TeamId, CriteriaParam, QuerySort} from "@lyra/domain";


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
    CreateSubscriber: (organizationId: OrganizationId,
                       subscribeId: SubscriberId) =>
      `${prefix}/organization/${organizationId.value}/newsletter/subscriber/${subscribeId.value}`,
    FetchAll: (organizationId: OrganizationId) =>
      `${prefix}/organization/${organizationId.value}/newsletter/subscriber`,
  },
  Form:{
    FetchAll:(organizationId: OrganizationId)  => `${prefix}/organization/${organizationId.value}/form`,
    FetchDetail : (id: FormId) => `${prefix}/form/${id.value}`,
    CreateForm :(organizationId: OrganizationId,id: FormId)  => `${prefix}/organization/${organizationId.value}/form/${id.value}`,
    UpdateForm :(organizationId: OrganizationId,id: FormId)  => `${prefix}/organization/${organizationId.value}/form/${id.value}/update`,
    DeleteForm :(organizationId: OrganizationId, id: FormId)  => `${prefix}/organization/${organizationId.value}/form/${id.value}`,
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

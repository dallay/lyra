
/*
* Prefix /api for api server
*/


import {OrganizationId} from "~/domain/organization";
import {SubscriberId} from "~/domain/subscriber";
import {FormId} from "~/domain/forms";
import {TeamId} from "~/domain/team";
import type TagId from "~/domain/tag/TagId";

const prefix : string = '/api';

/*
* Prefix /api/another for another server
*
*/
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
    CountByStatus: (organizationId: OrganizationId) =>
      `${prefix}/organization/${organizationId.value}/newsletter/subscriber/count-by-status`,
    CountByTags: (organizationId: OrganizationId) =>
      `${prefix}/organization/${organizationId.value}/newsletter/subscriber/count-by-tags`,
  },
  Tag: {
    CreateTag: (organizationId: OrganizationId, tagId: TagId) =>
      `${prefix}/organization/${organizationId.value}/tag/${tagId.value}`,
    FetchAll: (organizationId: OrganizationId) =>
      `${prefix}/organization/${organizationId.value}/tag`,
    UpdateTag: (organizationId: OrganizationId, tagId: TagId) =>
      `${prefix}/organization/${organizationId.value}/tag/${tagId.value}/update`,
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


/*
* Prefix /api for api server
*/
import type {FormId} from "@lyra/api-services";

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
  }
}

export default Routes

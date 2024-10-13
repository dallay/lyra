import { joinURL } from 'ufo';
import { Routes } from './routes.server';
import { defineEventHandler, proxyRequest, useRuntimeConfig } from '#imports';

export default defineEventHandler(async (event) => {
  const config = useRuntimeConfig();
  const coreUrl = config.apiUrl;
  const anotherUrl = config.backendUrl;

  let path = '';
  let target = '';
  /*
   * Case: /api
   * */
  if (event.path.startsWith(Routes.core.name)) {
    path = event.path.replace(Routes.core.name, '');
    target = joinURL(coreUrl, path);
  } else if (event.path.startsWith(Routes.another.name)) {
    /*
     * Case: /api/another
     * */
    path = event.path.replace(Routes.another.regex, '');
    target = joinURL(anotherUrl, path);
  }

  return proxyRequest(event, target);
});

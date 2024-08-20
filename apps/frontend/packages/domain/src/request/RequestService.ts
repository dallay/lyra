import type { FetchOptions, FetchRequest, FetchResponse } from 'ofetch';
export const REQUEST_SERVICE = 'REQUEST_SERVICE';
export interface RequestService {
  request<T>(request: FetchRequest, options?: FetchOptions): Promise<FetchResponse<T>>;
}

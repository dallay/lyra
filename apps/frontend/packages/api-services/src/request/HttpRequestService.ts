import type { RequestService } from '~/request/RequestService';
import type { FetchOptions, FetchRequest, FetchResponse } from 'ofetch';
import createFetcher from '@/request/request.ts';
import { injectable } from 'inversify';

@injectable()
export class HttpRequestService implements RequestService {
  private readonly fetcher: <T>(request: FetchRequest, options?: FetchOptions) => Promise<FetchResponse<T>>;

  constructor(apiUrl: string) {
    this.fetcher = createFetcher(apiUrl);
  }

  async request<T>(request: FetchRequest, options?: FetchOptions): Promise<FetchResponse<T>> {
    return this.fetcher(request, options);
  }
}

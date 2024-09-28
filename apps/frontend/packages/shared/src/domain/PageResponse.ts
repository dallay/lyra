import type DataResponse from './DataResponse.ts';

export default interface PageResponse<T> extends DataResponse<T> {
  readonly prevPageCursor: string | undefined | null;
  readonly nextPageCursor: string | undefined | null;
}

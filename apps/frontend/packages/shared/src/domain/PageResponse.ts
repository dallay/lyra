import type ApiResponse from './ApiResponse.ts'

export default interface PageResponse<T> extends ApiResponse {
  readonly data: T[],
  readonly prevPageCursor: string | undefined | null,
  readonly nextPageCursor: string | undefined | null,
}

import type ApiResponse from './ApiResponse.ts'

export default interface PageResponse<T> extends ApiResponse {
  readonly data: T[],
  readonly nextPageCursor: string | undefined | null,
}

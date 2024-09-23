import type ApiResponse from './ApiResponse.ts'

export default interface DataResponse<T> extends ApiResponse {
  readonly data: T[]
}


export default interface ResponseData<T> extends Response {
  readonly data: T[],
  readonly nextPageCursor: string | null,
}

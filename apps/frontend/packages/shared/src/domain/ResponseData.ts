
export default interface ResponseData<T> extends Response {
  readonly data: T[],
}

import type Query from './Query';
import type ApiResponse from '../ApiResponse.ts';

export default interface QueryHandler<Q extends Query, R extends ApiResponse> {
  handle(query: Q): Promise<R>;
}

import type Query from './Query';
import type ApiResponse from '../ApiResponse.ts';
export default interface QueryBus {
	ask<R extends ApiResponse>(query: Query): Promise<R>;
}

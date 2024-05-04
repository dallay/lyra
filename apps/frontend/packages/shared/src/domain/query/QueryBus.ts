import type Query from './Query';
import type Response from '../Response.ts';
export default interface QueryBus {
	ask<R extends Response>(query: Query): Promise<R>;
}

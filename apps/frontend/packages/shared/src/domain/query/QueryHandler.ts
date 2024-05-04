import type Query from './Query';
import type Response from '../Response';

export default interface QueryHandler<Q extends Query, R extends Response> {
	handle(query: Q): Promise<R>;
}

import type { Query } from '@lyra/shared';
import type { CriteriaParam, QuerySort } from '~/types/types.ts';

export default class SearchFormsQuery implements Query {
	constructor(
		public readonly criteria?: CriteriaParam,
		public readonly size?: number,
		public readonly cursor?: string,
		public readonly sort?: QuerySort
	) {}
}

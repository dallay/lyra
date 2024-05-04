import type { Query } from '@lyra/shared';

export default class FindFormQuery implements Query {
	constructor(public readonly id: string) {}
}

import type { QueryHandler } from '@lyra/shared';
import FindFormQuery from '~/forms/application/find/FindFormQuery.ts';
import FormResponse from '~/forms/application/FormResponse.ts';
import { inject, injectable } from 'inversify';
import FormFinder, { FORM_FINDER_PROVIDER } from '~/forms/application/find/FormFinder.ts';

export const FIND_FORM_QUERY_HANDLER_PROVIDER = 'FIND_FORM_QUERY_HANDLER_PROVIDER';

@injectable()
export default class FindFormQueryHandler implements QueryHandler<FindFormQuery, FormResponse> {
	constructor(@inject(FORM_FINDER_PROVIDER) private formFinder: FormFinder) {}

	async handle(query: FindFormQuery): Promise<FormResponse> {
		return this.formFinder.find(query.id);
	}
}

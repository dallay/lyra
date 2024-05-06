import type { QueryHandler } from '@lyra/shared';
import FindFormQuery from '~/forms/application/find/FindFormQuery.ts';
import FormResponse from '~/forms/application/FormResponse.ts';
import { inject, injectable } from 'inversify';
import FormFinder from '~/forms/application/find/FormFinder.ts';
import { FORM_FINDER_PROVIDER } from '~/di/forms/forms.module.types.ts';

@injectable()
export default class FindFormQueryHandler implements QueryHandler<FindFormQuery, FormResponse> {
	constructor(@inject(FORM_FINDER_PROVIDER) private formFinder: FormFinder) {}

	async handle(query: FindFormQuery): Promise<FormResponse> {
		return this.formFinder.find(query.id);
	}
}

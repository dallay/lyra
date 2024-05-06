import FormsSearcher from './FormsSearcher.ts';
import SearchFormsQuery from './SearchFormsQuery.ts';
import type { QueryHandler } from '@lyra/shared';
import type { PageResponse } from '~/types/types.ts';
import type { Form } from '~/forms/domain/Form.ts';
import FormResponse from '~/forms/application/FormResponse.ts';
import { inject, injectable } from 'inversify';
import { FORM_SEARCHER_PROVIDER } from '~/di/forms/forms.module.types.ts';

@injectable()
export default class SearchFormsQueryHandler
	implements QueryHandler<SearchFormsQuery, PageResponse<FormResponse>>
{
	constructor(@inject(FORM_SEARCHER_PROVIDER) private readonly formsSearcher: FormsSearcher) {}

	async handle(query: SearchFormsQuery): Promise<PageResponse<FormResponse>> {
		const pageResponse: Promise<PageResponse<Form>> = this.formsSearcher.search(query);

		const page = await pageResponse;
		return {
			...page,
			data: page.data.map((form: Form) => FormResponse.from(form)),
		};
	}
}

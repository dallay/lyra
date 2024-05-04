import type FormRepository from '~/forms/domain/FormRepository.ts';
import { FORM_REPOSITORY_PROVIDER } from '~/forms/domain/FormRepository.ts';
import type { Form } from '~/forms/domain/Form.ts';
import type { PageResponse } from '~/types/types.ts';
import type SearchFormsQuery from '~/forms/application/search/SearchFormsQuery.ts';
import { inject, injectable } from 'inversify';

export const FORM_SEARCHER_PROVIDER = 'FORM_SEARCHER_PROVIDER';

@injectable()
export default class FormsSearcher {
	constructor(@inject(FORM_REPOSITORY_PROVIDER) private readonly formRepository: FormRepository) {}

	async search(query: SearchFormsQuery): Promise<PageResponse<Form>> {
		return await this.formRepository.search(query.criteria, query.sort, query.size, query.cursor);
	}
}

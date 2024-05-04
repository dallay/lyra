import type { Form } from '~/forms/domain/Form.ts';
import type { PageResponse } from '~/types/types.ts';
import type SearchFormsQuery from '~/forms/application/search/SearchFormsQuery.ts';
import { inject, injectable } from 'inversify';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import { FORM_FINDER_REPOSITORY_PROVIDER } from '~/forms/domain/FormFinderRepository.ts';

export const FORM_SEARCHER_PROVIDER = 'FORM_SEARCHER_PROVIDER';

@injectable()
export default class FormsSearcher {
	constructor(
		@inject(FORM_FINDER_REPOSITORY_PROVIDER) private readonly repository: FormFinderRepository
	) {}

	async search(query: SearchFormsQuery): Promise<PageResponse<Form>> {
		return await this.repository.search(query.criteria, query.sort, query.size, query.cursor);
	}
}

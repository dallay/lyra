import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import { inject, injectable } from 'inversify';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import { FORM_FINDER_REPOSITORY_PROVIDER } from '~/di/forms/forms.module.types.ts';
import type FormResponse from '~/forms/domain/FormResponse.ts';

export interface SearchFormsQuery {
	readonly criteria?: CriteriaParam;
	readonly size?: number;
	readonly cursor?: string;
	readonly sort?: QuerySort;
}

@injectable()
export default class FormsSearcher {
	constructor(
		@inject(FORM_FINDER_REPOSITORY_PROVIDER) private readonly repository: FormFinderRepository
	) {
    /* ... */
  }

	async search(query: SearchFormsQuery): Promise<PageResponse<FormResponse>> {
		return await this.repository.search(query.criteria, query.sort, query.size, query.cursor);
	}
}

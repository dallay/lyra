import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import type { Form } from './Form.ts';

export const FORM_FINDER_REPOSITORY_PROVIDER = 'FORM_FINDER_REPOSITORY_PROVIDER';

export default interface FormFinderRepository {
	search(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size?: number,
		cursor?: string
	): Promise<PageResponse<Form>>;

	find(id: string): Promise<Form>;
}

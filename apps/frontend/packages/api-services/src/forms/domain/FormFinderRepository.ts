import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import type { Form } from './Form.ts';

export default interface FormFinderRepository {
	search(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size?: number,
		cursor?: string
	): Promise<PageResponse<Form>>;

	find(id: string): Promise<Form>;
}

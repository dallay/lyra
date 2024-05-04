import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import type { Form } from './Form.ts';

export const FORM_REPOSITORY_PROVIDER = 'FORM_REPOSITORY_PROVIDER';

export default interface FormRepository {
	search(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size?: number,
		cursor?: string
	): Promise<PageResponse<Form>>;

	findForm(id: string): Promise<Form>;

	updateForm(form: Form): Promise<void>;
}

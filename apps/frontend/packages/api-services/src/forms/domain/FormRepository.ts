import type { Form } from './Form.ts';

export const FORM_REPOSITORY_PROVIDER = 'FORM_REPOSITORY_PROVIDER';

export default interface FormRepository {
	create(form: Form): Promise<void>;

	update(form: Form): Promise<void>;
}

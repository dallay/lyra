import type Form from './Form.ts';

export default interface FormRepository {
	create(form: Form): Promise<void>;

	update(form: Form): Promise<void>;
}

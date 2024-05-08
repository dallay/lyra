import { inject, injectable } from 'inversify';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import type Form from '~/forms/domain/Form.ts';
import { FORM_REPOSITORY_PROVIDER } from '~/di/forms/forms.module.types.ts';

@injectable()
export default class FormCreator {
	constructor(@inject(FORM_REPOSITORY_PROVIDER) private readonly repository: FormRepository) {}

	async create(form: Form): Promise<void> {
		await this.repository.create(form);
	}
}

import type FormRepository from '~/forms/domain/FormRepository.ts';
import { FORM_REPOSITORY_PROVIDER } from '~/forms/domain/FormRepository.ts';
import type { Form } from '~/forms/domain/Form.ts';
import FormNotFoundError from '~/forms/domain/FormNotFoundError.ts';
import { inject, injectable } from 'inversify';

export const FORM_FINDER_PROVIDER = 'FORM_FINDER_PROVIDER';
@injectable()
export default class FormFinder {
	constructor(@inject(FORM_REPOSITORY_PROVIDER) private readonly formRepository: FormRepository) {}

	async find(formId: string): Promise<Form> {
		const form = await this.formRepository.findForm(formId);

		if (!form) {
			throw new FormNotFoundError(formId);
		}

		return form;
	}
}

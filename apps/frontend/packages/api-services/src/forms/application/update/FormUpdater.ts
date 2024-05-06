import { inject, injectable } from 'inversify';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import type { Form } from '~/forms/domain/Form.ts';
import { FORM_REPOSITORY_PROVIDER } from '~/di/forms/forms.module.types.ts';

/**
 * @class FormUpdater
 *
 * @description A class that provides the functionality to update forms.
 * It uses dependency injection to get an instance of the FormRepository.
 */
@injectable()
export default class FormUpdater {
	/**
	 * @constructor
	 *
	 * @param {FormRepository} formRepository - An instance of FormRepository.
	 *
	 * @description The constructor uses inversify's @inject decorator to inject
	 * an instance of FormRepository.
	 */
	constructor(@inject(FORM_REPOSITORY_PROVIDER) private readonly formRepository: FormRepository) {}

	/**
	 * @method update
	 *
	 * @param {Form} form - The form to update.
	 *
	 * @returns {Promise<void>}
	 *
	 * @description This method updates a form by calling the updateForm method
	 * on the formRepository instance.
	 */
	async update(form: Form): Promise<void> {
		await this.formRepository.update(form);
	}
}

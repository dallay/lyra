import type { CommandHandler } from '@lyra/shared';
import { inject, injectable } from 'inversify';
import UpdateFormCommand from '~/forms/application/update/UpdateFormCommand.ts';
import FormUpdater from '~/forms/application/update/FormUpdater.ts';
import type { Form } from '~/forms/domain/Form.ts';
import { FORM_UPDATER_PROVIDER } from '~/di/forms/forms.module.types.ts';

/**
 * @class UpdateFormCommandHandler
 *
 * @description A class that implements the CommandHandler interface for the UpdateFormCommand.
 * It uses dependency injection to get an instance of the FormUpdater.
 */
@injectable()
export default class UpdateFormCommandHandler implements CommandHandler<UpdateFormCommand> {
	/**
	 * @constructor
	 *
	 * @param {FormUpdater} formUpdater - An instance of FormUpdater.
	 *
	 * @description The constructor uses inversify's @inject decorator to inject
	 * an instance of FormUpdater.
	 */
	constructor(@inject(FORM_UPDATER_PROVIDER) private readonly formUpdater: FormUpdater) {}

	/**
	 * @method handle
	 *
	 * @param {UpdateFormCommand} command - The command to handle.
	 *
	 * @returns {Promise<void>}
	 *
	 * @description This method handles the UpdateFormCommand by creating a form object
	 * from the command and then calling the update method on the formUpdater instance.
	 */
	async handle(command: UpdateFormCommand): Promise<void> {
		const form = {
			id: command.id,
			name: command.name,
			header: command.header,
			description: command.description,
			inputPlaceholder: command.inputPlaceholder,
			buttonText: command.buttonText,
			buttonColor: command.buttonColor,
			backgroundColor: command.backgroundColor,
			textColor: command.textColor,
			buttonTextColor: command.buttonTextColor,
		} as Form;
		await this.formUpdater.update(form);
	}
}

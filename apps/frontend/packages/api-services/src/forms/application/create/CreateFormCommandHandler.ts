import type { CommandHandler } from '@lyra/shared';
import { inject, injectable } from 'inversify';
import CreateFormCommand from '~/forms/application/create/CreateFormCommand.ts';
import FormCreator from '~/forms/application/create/FormCreator.ts';
import type { Form } from '~/forms/domain/Form.ts';
import { FORM_CREATOR_PROVIDER } from '~/di/forms/forms.module.types.ts';

@injectable()
export default class CreateFormCommandHandler implements CommandHandler<CreateFormCommand> {
	constructor(@inject(FORM_CREATOR_PROVIDER) private readonly formCreator: FormCreator) {}
	async handle(command: CreateFormCommand): Promise<void> {
		await this.formCreator.create({
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
		} as Form);
	}
}

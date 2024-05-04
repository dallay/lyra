import type { CommandHandler } from '@lyra/shared';
import { injectable, inject } from 'inversify';
import CreateFormCommand from '~/forms/application/create/CreateFormCommand.ts';
import FormCreator, { FORM_CREATOR_PROVIDER } from '~/forms/application/create/FormCreator.ts';
import type { Form } from '~/forms/domain/Form.ts';

export const CREATE_FORM_COMMAND_HANDLER_PROVIDER = 'CREATE_FORM_COMMAND_HANDLER_PROVIDER';

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

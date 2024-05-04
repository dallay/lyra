import type { CommandHandler } from '@lyra/shared';
import { inject, injectable } from 'inversify';
import DeleteFormCommand from '~/forms/application/delete/DeleteFormCommand.ts';
import FormDestroyer, {
	FORM_DESTROYER_PROVIDER,
} from '~/forms/application/delete/FormDestroyer.ts';

export const DELETE_FORM_COMMAND_HANDLER_PROVIDER = 'DELETE_FORM_COMMAND_HANDLER_PROVIDER';

@injectable()
export default class DeleteFormCommandHandler implements CommandHandler<DeleteFormCommand> {
	constructor(@inject(FORM_DESTROYER_PROVIDER) private readonly formDestroyer: FormDestroyer) {}
	handle(command: DeleteFormCommand): Promise<void> {
		return this.formDestroyer.destroy(command.id);
	}
}

import type { CommandHandler } from '@lyra/shared';
import { inject, injectable } from 'inversify';
import DeleteFormCommand from '~/forms/application/delete/DeleteFormCommand.ts';
import FormDestroyer from '~/forms/application/delete/FormDestroyer.ts';
import { FORM_DESTROYER_PROVIDER } from '~/di/forms/forms.module.types.ts';

@injectable()
export default class DeleteFormCommandHandler implements CommandHandler<DeleteFormCommand> {
	constructor(@inject(FORM_DESTROYER_PROVIDER) private readonly formDestroyer: FormDestroyer) {}
	handle(command: DeleteFormCommand): Promise<void> {
		return this.formDestroyer.destroy(command.id);
	}
}

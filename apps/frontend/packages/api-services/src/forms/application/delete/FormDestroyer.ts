import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import { inject, injectable } from 'inversify';
import { FORM_DESTROYER_REPOSITORY_PROVIDER } from '~/di/forms/forms.module.types.ts';

@injectable()
export default class FormDestroyer {
	constructor(
		@inject(FORM_DESTROYER_REPOSITORY_PROVIDER) private readonly repository: FormDestroyerRepository
	) {
    /* ... */
  }

	async destroy(id: string): Promise<void> {
		await this.repository.delete(id);
	}
}

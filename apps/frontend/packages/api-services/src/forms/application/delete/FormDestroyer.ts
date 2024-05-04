import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import { FORM_DESTROYER_REPOSITORY_PROVIDER } from '~/forms/domain/FormDestroyerRepository.ts';
import { inject, injectable } from 'inversify';

export const FORM_DESTROYER_PROVIDER = 'FORM_DESTROYER_PROVIDER';

@injectable()
export default class FormDestroyer {
	constructor(
		@inject(FORM_DESTROYER_REPOSITORY_PROVIDER) private readonly repository: FormDestroyerRepository
	) {}

	async destroy(id: string): Promise<void> {
		await this.repository.delete(id);
	}
}

import { injectable, inject } from 'inversify';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import { FORM_REPOSITORY_PROVIDER } from '~/forms/domain/FormRepository.ts';
import type { Form } from '~/forms/domain/Form.ts';

export const FORM_CREATOR_PROVIDER = 'FORM_CREATOR_PROVIDER';

@injectable()
export default class FormCreator {
  constructor(@inject(FORM_REPOSITORY_PROVIDER) private readonly repository: FormRepository) {
  }

  async create(form: Form): Promise<void> {
    await this.repository.create(form);
  }
}

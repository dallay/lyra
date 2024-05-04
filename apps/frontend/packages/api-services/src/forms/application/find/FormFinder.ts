import type { Form } from '~/forms/domain/Form.ts';
import FormNotFoundError from '~/forms/domain/FormNotFoundError.ts';
import { inject, injectable } from 'inversify';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import { FORM_FINDER_REPOSITORY_PROVIDER } from '~/forms/domain/FormFinderRepository.ts';

export const FORM_FINDER_PROVIDER = 'FORM_FINDER_PROVIDER';
@injectable()
export default class FormFinder {
  constructor(@inject(FORM_FINDER_REPOSITORY_PROVIDER) private readonly repository: FormFinderRepository) {
  }

  async find(formId: string): Promise<Form> {
    const form = await this.repository.find(formId);

    if (!form) {
      throw new FormNotFoundError(formId);
    }

    return form;
  }
}

import 'reflect-metadata';
import { mock, type MockProxy } from 'vitest-mock-extended';
import FormFinder from '~/forms/application/find/FormFinder.ts';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import FormNotFoundError from '~/forms/domain/FormNotFoundError.ts';
import Form from '~/forms/domain/Form.ts';
import FormResponse from '~/forms/domain/FormResponse.ts';

let formFinder: FormFinder;
let formRepository: MockProxy<FormFinderRepository>;
let form: Form;

describe('FormFinder', () => {
  beforeEach(() => {
    formRepository = mock<FormFinderRepository>();
    formFinder = new FormFinder(formRepository);
    form = Form.fromPrimitives({
      id: crypto.randomUUID(),
      name: 'Form',
      header: 'Form header',
      description: 'Form description',
      textColor: '#000000',
      buttonColor: '#000000',
      buttonText: 'Submit',
      inputPlaceholder: 'Input placeholder',
      backgroundColor: '#FFFFFF',
      buttonTextColor: '#FFFFFF',
    });
  });

  it('finds a form successfully', async () => {
    const formResponse = FormResponse.from(form);
    formRepository.find.mockReturnValue(Promise.resolve(formResponse));

    const result = await formFinder.find(form.id.value);

    expect(result).toEqual(formResponse);
    expect(formRepository.find).toHaveBeenCalledWith(form.id.value);
  });

  it('throws an error when form is not found', async () => {
    formRepository.find.mockReturnValue(Promise.resolve(undefined as unknown as FormResponse));

    await expect(formFinder.find(form.id.value)).rejects.toThrow(FormNotFoundError);
    expect(formRepository.find).toHaveBeenCalledWith(form.id.value);
  });
});

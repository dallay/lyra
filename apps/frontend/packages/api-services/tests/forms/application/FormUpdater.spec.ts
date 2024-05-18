import 'reflect-metadata';
import { describe, beforeEach, it, expect } from 'vitest';
import { mock, type MockProxy } from 'vitest-mock-extended';
import FormUpdater from '@/forms/application/update/FormUpdater.ts';
import type FormRepository from '@/forms/domain/FormRepository.ts';
import Form from '@/forms/domain/Form.ts';

describe('FormUpdater', () => {
  let formUpdater: FormUpdater;
  let formRepository: MockProxy<FormRepository>;
  let form: Form;

  beforeEach(() => {
    formRepository = mock<FormRepository>();
    formUpdater = new FormUpdater(formRepository);
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

  it('updates form successfully', async () => {
    formRepository.update.mockResolvedValue();

    await formUpdater.update(form);

    expect(formRepository.update).toHaveBeenCalledWith(form);
  });

  it('throws an error when form update fails', async () => {
    formRepository.update.mockRejectedValue(new Error('Update failed'));

    await expect(formUpdater.update(form)).rejects.toThrow('Update failed');
    expect(formRepository.update).toHaveBeenCalledWith(form);
  });
});

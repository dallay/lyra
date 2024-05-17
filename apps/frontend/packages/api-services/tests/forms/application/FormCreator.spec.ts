import 'reflect-metadata';
import { type MockProxy, mock } from 'vitest-mock-extended';
import { describe, beforeEach, it, expect } from 'vitest';
import FormCreator from '@/forms/application/create/FormCreator.ts';
import Form from '@/forms/domain/Form.ts';
import type FormRepository from '@/forms/domain/FormRepository.ts';

describe('FormCreator', () => {
	let formCreator: FormCreator;
	let formRepository: MockProxy<FormRepository>;
	let form: Form;

	beforeEach(() => {
		formRepository = mock<FormRepository>();
		formCreator = new FormCreator(formRepository);
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

	it('creates a form successfully', async () => {
		formRepository.create.mockResolvedValue();

		await formCreator.create(form);

		expect(formRepository.create).toHaveBeenCalledWith(form);
	});

	it('throws an error when form creation fails', async () => {
		formRepository.create.mockRejectedValue(new Error('Form creation failed'));

		await expect(formCreator.create(form)).rejects.toThrow('Form creation failed');

		expect(formRepository.create).toHaveBeenCalledWith(form);
	});
});

import 'reflect-metadata';
import { type MockProxy, mock } from 'vitest-mock-extended';
import FormDestroyer from '~/forms/application/delete/FormDestroyer.ts';
import Form from '~/forms/domain/Form.ts';
import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';

describe('FormDestroyer', () => {
	let formCreator: FormDestroyer;
	let formRepository: MockProxy<FormDestroyerRepository>;
	let form: Form;

	beforeEach(() => {
		formRepository = mock<FormDestroyerRepository>();
		formCreator = new FormDestroyer(formRepository);
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

	it('destroys a form successfully', async () => {
		formRepository.delete.mockResolvedValue();

		await formCreator.destroy(form.id.value);

		expect(formRepository.delete).toHaveBeenCalledWith(form.id.value);
	});
});

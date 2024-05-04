import type { Response } from '@lyra/shared';
import type { Form } from '~/forms/domain/Form.ts';

export default class FormResponse implements Response {
	constructor(
		public readonly id: string,
		public readonly name: string,
		public readonly header: string,
		public readonly description: string,
		public readonly inputPlaceholder: string,
		public readonly buttonText: string,
		public readonly buttonColor: string,
		public readonly backgroundColor: string,
		public readonly textColor: string,
		public readonly buttonTextColor: string,
		public readonly createdAt: string | null = null,
		public readonly updatedAt: string | null = null
	) {}

	static from(form: Form): FormResponse {
		return new FormResponse(
			form.id,
			form.name,
			form.header,
			form.description,
			form.inputPlaceholder,
			form.buttonText,
			form.buttonColor,
			form.backgroundColor,
			form.textColor,
			form.buttonTextColor,
			form.createdAt,
			form.updatedAt
		);
	}
}

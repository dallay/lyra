import type { Response } from '@lyra/shared';
import Form from '~/forms/domain/Form.ts';

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
	) {
    this.id = id;
    this.name = name;
    this.header = header;
    this.description = description;
    this.inputPlaceholder = inputPlaceholder;
    this.buttonText = buttonText;
    this.buttonColor = buttonColor;
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
    this.buttonTextColor = buttonTextColor;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

	static from(form: Form): FormResponse {
		return new FormResponse(
			form.id.value,
			form.name,
			form.header,
			form.description,
			form.inputPlaceholder,
			form.buttonText,
			form.buttonColor.value,
			form.backgroundColor.value,
			form.textColor.value,
			form.buttonTextColor.value,
			form.createdAt.toDateString(),
			form.updatedAt.toDateString()
		);
	}

	static to(formResponse: FormResponse): Form {
		return Form.fromPrimitives(formResponse);
	}
}

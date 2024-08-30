export default class FormNotFoundError extends Error {
	constructor(formId: string) {
		super(`Form with id <${formId}> not found`);
	}
}

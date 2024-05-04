import type { Command } from '@lyra/shared';

/**
 * The `UpdateFormCommand` class implements the `Command` interface.
 * This class is used to update the form with the provided parameters.
 */
export default class UpdateFormCommand implements Command {
	/**
	 * Constructs a new instance of the `UpdateFormCommand` class.
	 *
	 * @param id - The unique identifier of the form.
	 * @param name - The name of the form.
	 * @param header - The header text of the form.
	 * @param description - The description of the form.
	 * @param inputPlaceholder - The placeholder text for the input field of the form.
	 * @param buttonText - The text displayed on the form's button.
	 * @param buttonColor - The color of the form's button.
	 * @param backgroundColor - The background color of the form.
	 * @param textColor - The color of the form's text.
	 * @param buttonTextColor - The color of the form's button text.
	 */
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
		public readonly buttonTextColor: string
	) {}
}

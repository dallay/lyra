import FormId from '~/forms/FormId.ts';
import HexColor from '~/forms/HexColor.ts';

/**
 * Class representing a form.
 */
export default class Form {
  /**
   * Create a form.
   * @param {FormId} id - The unique identifier of the form.
   * @param {string} name - The name of the form.
   * @param {string} header - The header text of the form.
   * @param {string} description - The description of the form.
   * @param {string} inputPlaceholder - The placeholder text for the input field.
   * @param {string} buttonText - The text displayed on the button.
   * @param {HexColor} buttonColor - The color of the button.
   * @param {HexColor} backgroundColor - The background color of the form.
   * @param {HexColor} textColor - The color of the text in the form.
   * @param {HexColor} buttonTextColor - The color of the text on the button.
   * @param {string} organizationId - The unique identifier of the organization that owns the form.
   * @param {Date} createdAt - The date when the form was created.
   * @param {Date} updatedAt - The date when the form was last updated.
   */
  private constructor(
    readonly id: FormId,
    readonly name: string,
    readonly header: string,
    readonly description: string,
    readonly inputPlaceholder: string,
    readonly buttonText: string,
    readonly buttonColor: HexColor,
    readonly backgroundColor: HexColor,
    readonly textColor: HexColor,
    readonly buttonTextColor: HexColor,
    readonly organizationId: string,
    readonly createdAt: Date,
    readonly updatedAt: Date
  ) {
    this.id = id;
  }

  /**
   * Create a form from plain data.
   * @param {Object} plainData - The plain data object.
   * @param {string} plainData.id - The unique identifier of the form.
   * @param {string} plainData.name - The name of the form.
   * @param {string} plainData.header - The header text of the form.
   * @param {string} plainData.description - The description of the form.
   * @param {string} plainData.inputPlaceholder - The placeholder text for the input field.
   * @param {string} plainData.buttonText - The text displayed on the button.
   * @param {string} plainData.buttonColor - The color of the button.
   * @param {string} plainData.backgroundColor - The background color of the form.
   * @param {string} plainData.textColor - The color of the text in the form.
   * @param {string} plainData.buttonTextColor - The color of the text on the button.
   * @param {string} plainData.organizationId - The unique identifier of the organization that owns the form.
   * @returns {Form} The created form instance.
   */
  static fromPrimitives(plainData: {
    id: string;
    name: string;
    header: string;
    description: string;
    inputPlaceholder: string;
    buttonText: string;
    buttonColor: string;
    backgroundColor: string;
    textColor: string;
    buttonTextColor: string;
    organizationId: string;
  }): Form {
    return new Form(
      FormId.create(plainData.id),
      plainData.name,
      plainData.header,
      plainData.description,
      plainData.inputPlaceholder,
      plainData.buttonText,
      HexColor.create(plainData.buttonColor),
      HexColor.create(plainData.backgroundColor),
      HexColor.create(plainData.textColor),
      HexColor.create(plainData.buttonTextColor),
      plainData.organizationId,
      new Date(),
      new Date()
    );
  }

  /**
   * Convert the form instance to a plain data object.
   * @returns {Object} The plain data object.
   */
  toPrimitives() {
    return {
      id: this.id.value,
      name: this.name,
      header: this.header,
      description: this.description,
      inputPlaceholder: this.inputPlaceholder,
      buttonText: this.buttonText,
      buttonColor: this.buttonColor.value,
      backgroundColor: this.backgroundColor.value,
      textColor: this.textColor.value,
      buttonTextColor: this.buttonTextColor.value,
      organizationId: this.organizationId,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt,
    };
  }
}

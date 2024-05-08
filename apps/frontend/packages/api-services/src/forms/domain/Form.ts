import FormId from '~/forms/domain/FormId.ts';
import HexColor from '~/forms/domain/HexColor.ts';

export default class Form {
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
    readonly createdAt: Date,
    readonly updatedAt: Date) {
  }

  static fromPrimitives(plainData: {
    id: string,
    name: string,
    header: string,
    description: string,
    inputPlaceholder: string,
    buttonText: string,
    buttonColor: string,
    backgroundColor: string,
    textColor: string,
    buttonTextColor: string
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
      new Date(),
      new Date()
    );
  }

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
      createdAt: this.createdAt,
      updatedAt: this.updatedAt,
    };
  }
}

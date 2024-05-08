import { InvalidArgumentError, StringValueObject } from '@lyra/shared';

export default class HexColor extends StringValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidHexadecimalColor(value);
  }

  public static create(value: string): HexColor {
    return new HexColor(value);
  }

  private ensureIsValidHexadecimalColor(id: string): void {
    const regexExp = /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/;
    const isValid = regexExp.test(id);
    if (!isValid) {
      throw new InvalidArgumentError(`<${this.constructor.name}> does not allow the value <${id}>`);
    }
  }
}

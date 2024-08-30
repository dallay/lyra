import StringValueObject from './StringValueObject';
import InvalidArgumentError from "~/domain/vo/InvalidArgumentError.ts";

export default abstract class UUIDValueObject extends StringValueObject {
  protected constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  protected ensureIsValidUuid(id: string): void {
    const regexExp =
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/gi;

    const isValid = regexExp.test(id);
    if (!isValid) {
      throw new InvalidArgumentError(`<${this.constructor.name}> does not allow the value <${id}>`);
    }
  }
}

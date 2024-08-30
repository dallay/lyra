import { UUIDValueObject } from '@lyra/shared';

export default class FormId extends UUIDValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  static random(): FormId {
    return new FormId(crypto.randomUUID());
  }

  public static create(value: string): FormId {
    return new FormId(value);
  }
}

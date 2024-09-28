import { UUIDValueObject } from '@lyra/shared';

export default class TagId extends UUIDValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  static random(): TagId {
    return new TagId(crypto.randomUUID());
  }

  public static create(value: string): TagId {
    return new TagId(value);
  }
}

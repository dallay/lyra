import { UUIDValueObject } from '@lyra/shared';

export default class UserId extends UUIDValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  static random(): UserId {
    return new UserId(crypto.randomUUID());
  }

  public static create(value: string): UserId {
    return new UserId(value);
  }
}

import { UUIDValueObject } from '@lyra/shared';

export default class SubscriberId extends UUIDValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  static random(): SubscriberId {
    return new SubscriberId(crypto.randomUUID());
  }

  public static create(value: string): SubscriberId {
    return new SubscriberId(value);
  }
}

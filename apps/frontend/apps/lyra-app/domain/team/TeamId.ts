import { UUIDValueObject } from '@lyra/shared';

export default class TeamId extends UUIDValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  static random(): TeamId {
    return new TeamId(crypto.randomUUID());
  }

  public static create(value: string): TeamId {
    return new TeamId(value);
  }
}

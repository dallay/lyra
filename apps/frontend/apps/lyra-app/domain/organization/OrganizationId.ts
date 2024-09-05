import { UUIDValueObject } from '@lyra/shared';

export default class OrganizationId extends UUIDValueObject {
  private constructor(value: string) {
    super(value);
    this.ensureIsValidUuid(value);
  }

  static random(): OrganizationId {
    return new OrganizationId(crypto.randomUUID());
  }

  public static create(value: string): OrganizationId {
    return new OrganizationId(value);
  }
}

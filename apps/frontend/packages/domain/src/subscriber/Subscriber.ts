export interface Subscriber {
  readonly id: string,
  readonly email: string,
  readonly firstname?: string,
  readonly lastname?: string,
  readonly status: SubscriberStatus,
  readonly attributes?: Record<string, string>,
  readonly organizationId: string,
  readonly createdAt?: Date | string,
  readonly updatedAt?: Date | string
}

/**
 * Subscriber status ['ENABLED', 'DISABLED', 'BLOCKLISTED']
 * @enum {string}
 */
export enum SubscriberStatus {
  ENABLED = "ENABLED",
  DISABLED = "DISABLED",
  BLOCKLISTED = "BLOCKLISTED"
}

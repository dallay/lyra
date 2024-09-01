import { z } from 'zod';

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

export const subscriberSchema = z.object({
  id: z.string(),
  email: z.string(),
  firstname: z.string().optional(),
  lastname: z.string().optional(),
  status: z.nativeEnum(SubscriberStatus),
  attributes: z.record(z.string()).optional(),
  organizationId: z.string(),
  createdAt: z.union([z.date(), z.string()]).optional(),
  updatedAt: z.union([z.date(), z.string()]).optional()
});

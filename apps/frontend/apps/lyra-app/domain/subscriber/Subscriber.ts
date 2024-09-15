import {z} from 'zod';

export interface Attributes {
	[key: string]: string | string[] | number | boolean;
}

export interface Subscriber {
	readonly id: string;
	readonly email: string;
	readonly name?: string;
	readonly status: SubscriberStatus;
	readonly attributes?: Attributes;
	readonly organizationId: string;
	readonly createdAt?: Date | string;
	readonly updatedAt?: Date | string;
}

/**
 * Subscriber status ['ENABLED', 'DISABLED', 'BLOCKLISTED']
 * @enum {string}
 */
export enum SubscriberStatus {
	ENABLED = 'ENABLED',
	DISABLED = 'DISABLED',
	BLOCKLISTED = 'BLOCKLISTED',
}

export const subscriberSchema = z.object({
	id: z.string(),
	email: z.string(),
	name: z.string().optional(),
	status: z.nativeEnum(SubscriberStatus),
	attributes: z
		.record(z.union([z.string(), z.array(z.string()), z.number(), z.boolean()]))
		.optional(),
	organizationId: z.string(),
	createdAt: z.union([z.date(), z.string()]).optional(),
	updatedAt: z.union([z.date(), z.string()]).optional(),
});

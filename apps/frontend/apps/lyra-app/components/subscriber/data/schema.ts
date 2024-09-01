import { z } from 'zod'

// We're keeping a simple non-relational schema here.
// IRL, you will have a schema for your data models.
export const subscriberSchema = z.object({
  id: z.string(),
  email: z.string(),
  firstname: z.string().optional(),
  lastname: z.string().optional(),
  status: z.string(),
  attributes: z.object({
    priority: z.string().optional(),
    label: z.string().optional(),
  }).optional(),
  createdAt: z.string().optional(),
  updatedAt: z.string().optional()
})

export type Subscriber = z.infer<typeof subscriberSchema>

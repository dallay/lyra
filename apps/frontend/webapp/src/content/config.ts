import { defineCollection, reference, z } from 'astro:content';

const highlights = defineCollection({
  type: 'data',
  schema: z.object({
    title: z.string(),
    description: z.string(),
    icon: z.string().optional()
  }),
});
export const collections = {
  highlights
};

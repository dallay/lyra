import type { TagColors } from "./Tag";

/**
 * Interface representing a Tag object.
 *
 * @interface Tag
 * @property {string} id - The unique identifier of the tag.
 * @property {string} name - The name of the tag.
 * @property {string} color - The color associated with the tag.
 * @property {Date | string} [createdAt] - The date the tag was created.
 * @property {Date | string} [updatedAt] - The date the tag was last updated.
 */
export interface TagResponse {
  readonly id: string;
  readonly name: string;
  readonly color: string;
  readonly subscribers: string[];
  readonly createdAt?: Date | string;
  readonly updatedAt?: Date | string;
}

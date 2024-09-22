/**
 * Interface representing a request to create or update a Tag.
 *
 * @interface TagRequest
 * @property {string} name - The name of the tag.
 * @property {string} [color] - The optional color associated with the tag.
 * @property {string[]} [subscribers] - The optional list of user emails to subscribe to the tag.
 */
export interface TagRequest {
  name: string;
  color?: string;
  subscribers?: string[];
}

/**
 * Interface representing a request to create a Tag.
 *
 * @interface CreateTagRequest
 * @property {string} name - The name of the tag.
 * @property {string} [color] - The optional color associated with the tag.
 * @property {string[]} [subscribers] - The optional list of user emails to subscribe to the tag.
 */
export interface CreateTagRequest {
  name: string;
  color?: string;
  subscribers?: string[];
}

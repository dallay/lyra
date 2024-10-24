/**
 * Data class representing a link preview.
 *
 * @property title The title of the link.
 * @property description The description of the link.
 * @property imageUrl The URL of the image associated with the link.
 * @property url The URL of the link.
 */
export class LinkPreview {
  title: string;
  description: string;
  imageUrl?: string;
  url: string;

  constructor(title: string, description: string, imageUrl: string | undefined, url: string) {
    this.title = title;
    this.description = description;
    this.imageUrl = imageUrl;
    this.url = url;
  }
}

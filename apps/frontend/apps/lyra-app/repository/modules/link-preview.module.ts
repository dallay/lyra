import Routes from '../routes.client';
import SecureFetchFactory from '~/repository/secure.factory';
import type { LinkPreview } from '~/domain/linkpreview';

class LinkPreviewModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.LinkPreview;

  async fetchLinkPreview(url : string) {
    return this.call<LinkPreview>({
      method: 'GET',
      url: `${this.RESOURCE.Preview(url)}`,
      fetchOptions: {
        headers: {
          ...(this.accessToken
            ? {
                Authorization: `Bearer ${this.accessToken}`,
              }
            : {}),
        },
      },
    });
  }
}

export default LinkPreviewModule;

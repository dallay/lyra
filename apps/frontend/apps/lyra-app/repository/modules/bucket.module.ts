import Routes from '../routes.client';
import SecureFetchFactory from '~/repository/secure.factory';

class BucketModule extends SecureFetchFactory {
  private readonly RESOURCE = Routes.Bucket;

  async uploadFile(file: File) {
     // Simulate image upload
     return await new Promise<string>((resolve) => {
      console.log(`Uploading file: ${file.name} (${file.size} bytes) to ${this.RESOURCE.Upload()}`);
      console.log('Image upload is disabled in the demo... Please implement the API.uploadImage method in your project.');
      setTimeout(() => {
        resolve('https://picsum.photos/800')
      }, 1000) // Simulate a 1 second upload time
    })
  }
}

export default BucketModule;

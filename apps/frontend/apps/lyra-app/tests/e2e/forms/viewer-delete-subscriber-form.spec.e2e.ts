import { expect, test, Page } from '@playwright/test';
import {
  checkTableColumnContent,
  getSubscriberForms,
  initialScreenLoad,
} from '../helper/subscriberFormHelper';

async function init(page: Page) {
  await initialScreenLoad(page);
  await getSubscriberForms(page);
  await checkTableColumnContent(page, [
    {
      name: 'Form YAP 🚀',
    },
  ]);
  await page.getByTestId('view-form-trigger-5e26f5ef-d859-49ae-9b7a-5eec17ab6ca2').click();
}
test('view subscriber form', async ({ page }) => {
  await init(page);
  const modal = page.getByTestId('drawer').nth(2);
  await expect(modal).toBeVisible();
  const title = modal.locator('h5');
  await expect(title).toBeVisible();
  await expect(title).toHaveText(' Form YAP 🚀 ');
  await page.locator('form').filter({ hasText: 'Embed Options Below are' }).getByRole('button').first().click();
  await page.getByText('Copied to clipboard').isVisible();
  await page.locator('.i-fa-times').click();
});
test('delete subscriber form', async ({ page }) => {
  await init(page);
  const modal = page.getByTestId('drawer').nth(2);
  await expect(modal).toBeVisible();
  const title = modal.locator('h5');
  await expect(title).toBeVisible();
  await expect(title).toHaveText(' Form YAP 🚀 ');

  const deleteFormButton = page.getByTestId('deleteFormViewer');
  await deleteFormButton.click();
});

test('close drawer', async ({ page }) => {
  await init(page);
  const modal = page.getByTestId('drawer').nth(2);
  await expect(modal).toBeVisible();
  const closeButton = page.getByTestId('closeViewer');
  await closeButton.click();
});

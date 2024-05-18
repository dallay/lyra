import { expect, test, Page } from '@playwright/test';
import {
  checkTableColumnContent,
  getSubscriberForms,
  initialScreenLoad,
} from '../helper/subscriberFormHelper';
import { formRequest } from './FormRequestMock';

async function init(page: Page) {
  await initialScreenLoad(page);
  await getSubscriberForms(page);
  await checkTableColumnContent(page, [
    {
      name: 'Form YAP 🚀',
    },
  ]);
  await page.getByTestId('edit-form-trigger-5e26f5ef-d859-49ae-9b7a-5eec17ab6ca2').click();
}

test('update subscriber form', async ({ page }) => {
  await init(page);
  const modal = page.getByTestId('drawer').nth(1);
  await expect(modal).toBeVisible();
  const title = modal.locator('h5');
  await expect(title).toBeVisible();
  await expect(title).toHaveText(' Form Editor ');
  const formNameInput = page.getByTestId('nameEditor');
  await formNameInput.fill(`${formRequest.name} Updated`);
  const updateFormButton = page.getByTestId('updateFormEditor');
  await updateFormButton.click();
  await checkTableColumnContent(page, [
    {
      name: `${formRequest.name} Updated`,
    },
  ]);
});

test('close drawer', async ({ page }) => {
  await init(page);
  const modal = page.getByTestId('drawer').nth(1);
  await expect(modal).toBeVisible();
  const closeButton = page.getByTestId('closeEditor');
  await closeButton.click();
});

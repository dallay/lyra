import { expect, test } from '@playwright/test';
import {
  checkTableColumnContent,
  getSubscriberForms,
  initialScreenLoad,
} from '../helper/subscriberFormHelper';

test('create subscriber form', async ({ page }) => {
  await initialScreenLoad(page);
  await getSubscriberForms(page);
  await checkTableColumnContent(page, [
    {
      name: 'Form YAP 🚀',
    },

  ]);
  let createFormButton = page.getByTestId('create-form-trigger');
  await createFormButton.click();
  const modal = page.getByTestId('drawer').first();

  await expect(modal).toBeVisible();
  const title = modal.locator('h5');
  await expect(title).toBeVisible();
  await expect(title).toHaveText(' Form Editor ');

  const formNameInput = page.getByTestId('name');
  await formNameInput.fill(formRequest.name);
  const formHeaderInput = page.getByTestId('header');
  await formHeaderInput.fill(formRequest.header);
  const formDescriptionInput = page.getByTestId('description');
  await formDescriptionInput.fill(formRequest.description);
  const formInputPlaceholderInput = page.getByTestId('inputPlaceholder');
  await formInputPlaceholderInput.fill(formRequest.inputPlaceholder);
  const formButtonTextInput = page.getByTestId('buttonText');
  await formButtonTextInput.fill(formRequest.buttonText);
  await page.getByTestId('buttonColor').fill(formRequest.buttonColor);
  await page.getByTestId('buttonTextColor').fill(formRequest.buttonTextColor);
  await page.getByTestId('backgroundColor').fill(formRequest.backgroundColor);
  await page.getByTestId('textColor').fill(formRequest.textColor);
  createFormButton = page.getByTestId('createForm');
  await createFormButton.click();
  await checkTableColumnContent(page, [
    {
      name: formRequest.name,
    },
    {
      name: 'Form YAP 🚀',
    },
  ]);
});

const formRequest = {
  name: 'My Super Newsletter',
  header: 'Lyra\'s Newsletter',
  description: 'Super description',
  inputPlaceholder: 'Enter your email',
  buttonText: 'Subscribe',
  buttonColor: '#e5652e',
  backgroundColor: '#838c96',
  textColor: '#262628',
  buttonTextColor: '#efe8e8',
};

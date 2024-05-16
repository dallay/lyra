import { expect, test } from '@playwright/test';
import {
  checkTableColumnContent,
  getSubscriberForms,
  initialScreenLoad,
} from '../helper/subscriberFormHelper';
import { formRequest } from './FormRequestMock';

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
  await expect(title).toHaveText(' Form Creator ');

  const formNameInput = page.getByTestId('nameCreator');
  await formNameInput.fill(formRequest.name);
  const formHeaderInput = page.getByTestId('headerCreator');
  await formHeaderInput.fill(formRequest.header);
  const formDescriptionInput = page.getByTestId('descriptionCreator');
  await formDescriptionInput.fill(formRequest.description);
  const formInputPlaceholderInput = page.getByTestId('inputPlaceholderCreator');
  await formInputPlaceholderInput.fill(formRequest.inputPlaceholder);
  const formButtonTextInput = page.getByTestId('buttonTextCreator');
  await formButtonTextInput.fill(formRequest.buttonText);
  await page.getByTestId('buttonColorCreator').fill(formRequest.buttonColor);
  await page.getByTestId('buttonTextColorCreator').fill(formRequest.buttonTextColor);
  await page.getByTestId('backgroundColorCreator').fill(formRequest.backgroundColor);
  await page.getByTestId('textColorCreator').fill(formRequest.textColor);
  createFormButton = page.getByTestId('createFormCreator');
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


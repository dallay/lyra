import { expect, Page } from '@playwright/test';
import { signIn } from './authHelper';

export type SubscriberFormCell = {
  name: string;
};

export async function initialScreenLoad(page: Page) {
  await signIn({ page, startUrl: '/' });
  await getSubscriberForms(page);
  await page.goto('/dashboard', { waitUntil: 'networkidle' });
  await page.getByRole('navigation').getByText('Settings').click();
  await page.getByRole('navigation').getByText('Publication').click();
  await page.getByRole('navigation').getByRole('link', { name: 'Subscriber Form' }).click();
  await page.goto('settings/publication/subscriber-form', { waitUntil: 'networkidle' });
  await expect(
    page.getByRole('main').locator('h2').filter({ hasText: 'Subscriber Forms' }),
  ).toBeVisible();
}

export async function getSubscriberForms(page: Page) {
  await page.routeFromHAR('tests/e2e/hars/subscriber-form.har', {
    url: '**/api/forms**',
    update: false,
  });
  await page.goto('/settings/publication/subscriber-form', { waitUntil: 'networkidle' });
}

export async function checkTableColumnContent(page: Page, data: SubscriberFormCell[]) {
  const allRows = await page.locator('tbody').locator('tr').all();
  for (const rows of allRows) {
    const columns = rows.locator('td');
    const rowValues = await columns.allInnerTexts();
    const [name] = rowValues;
    const expectedData = data.find((d) => d.name === name);
    const message = `the expected data for ${name} is not found`;
    if (!expectedData) console.error(message);
    expect(expectedData).toBeDefined();
    expect(name).toBe(expectedData.name);
  }
}

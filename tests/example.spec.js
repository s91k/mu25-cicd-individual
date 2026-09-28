// @ts-check
import { test, expect } from '@playwright/test';

test('load book list', async ({ page }) => {
  await page.goto('');

  await expect(page.locator('.book-item').first()).toBeVisible();
});
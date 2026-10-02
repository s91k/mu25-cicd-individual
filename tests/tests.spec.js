// @ts-check
import { test, expect } from '@playwright/test';

test('load book list', async ({ page }) => {
  await page.goto('');

  await expect(page.locator('.book-item').first()).toBeVisible();
});

test('add and remove from cart', async ({ page }) => {
  await page.goto('/books/1');

  await page.locator('.buy-button').click();

  await page.goto('/books/2');

  await page.locator('.buy-button').click();

  await page.goto('/cart');

  await expect(page.locator('.cart > article')).toHaveCount(2);

  await page.locator('.cart > article > .remove-button').first().click();
  await page.locator('.cart > article > .remove-button').first().click();

  await expect(page.locator('.cart > article')).toHaveCount(0);
});

test('buy book', async ({ page }) => {
  await page.goto('/books/1');

  await page.locator('.buy-button').click();

  await page.goto('/checkout');

  await page.locator('#email').fill("test@test.com");
  await page.locator('#firstName').fill("Test");
  await page.locator('#lastName').fill("Testsson");
  await page.locator('#streetAddress').fill("Testgatan 5");
  await page.locator('#postalCode').fill("12345");
  await page.locator('#city').fill("Testköping");
  await page.locator('#submit').click();

  await expect(page).toHaveURL(/\/orders\//);
  await expect(page.locator('#email').first()).toContainText("test@test.com");
});
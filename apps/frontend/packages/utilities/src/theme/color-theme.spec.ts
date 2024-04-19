/**
 * @vitest-environment happy-dom
 */

import { isDarkMode, loadTheme, toggleTheme } from './color-theme.ts';
import { beforeEach, describe, expect, test, vi } from 'vitest';

describe('Color Theme', () => {
	beforeEach(() => {
		window.matchMedia = vi.fn().mockImplementation((query) => {
			return {
				matches: query === '(prefers-color-scheme: dark)',
				media: query,
				onchange: null,
				addListener: vi.fn(),
				removeListener: vi.fn(),
				addEventListener: vi.fn(),
				removeEventListener: vi.fn(),
				dispatchEvent: vi.fn(),
			};
		});
	});

	test('isDarkMode returns true when dark mode is set in localStorage', () => {
		localStorage.setItem('theme', 'dark');
		expect(isDarkMode()).toBe(true);
	});

	test('isDarkMode returns false when light mode is set in localStorage', () => {
		localStorage.setItem('theme', 'light');
		expect(isDarkMode()).toBe(false);
	});

	test('isDarkMode returns false when no theme is set in localStorage and system prefers dark mode', () => {
		expect(isDarkMode()).toBe(false);
	});

	test('loadTheme loads dark theme from localStorage', () => {
		localStorage.setItem('theme', 'dark');
		loadTheme();
		expect(document.documentElement.classList.contains('dark')).toBe(true);
	});

	test('loadTheme loads light theme from localStorage', () => {
		localStorage.setItem('theme', 'light');
		loadTheme();
		expect(document.documentElement.classList.contains('light')).toBe(true);
	});

	test('loadTheme loads dark theme when no theme is set in localStorage and system prefers dark mode', () => {
		loadTheme();
		expect(document.documentElement.classList.contains('dark')).toBe(true);
	});

	test('toggleTheme toggles to dark theme when light mode is set', () => {
		localStorage.setItem('theme', 'light');
		toggleTheme();
		expect(localStorage.getItem('theme')).toBe('dark');
	});

	test('toggleTheme toggles to light theme when dark mode is set', () => {
		localStorage.setItem('theme', 'dark');
		toggleTheme();
		expect(localStorage.getItem('theme')).toBe('light');
	});

	test('loadTheme adds event listener for theme-changed', () => {
		const addEventListenerSpy = vi.spyOn(document, 'addEventListener');
		loadTheme();
		expect(addEventListenerSpy).toHaveBeenCalledWith('theme-changed', expect.any(Function));
	});
});

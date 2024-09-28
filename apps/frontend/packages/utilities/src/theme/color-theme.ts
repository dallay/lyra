const DEFAULT_KEY = 'theme';
const DARK_THEME = 'dark';
const LIGHT_THEME = 'light';

export interface ThemeChangedEventDetail {
  isDark: boolean;
}
/**
 * Checks whether dark mode is enabled or not.
 * @param {string} key - The key in localStorage where the theme is stored.
 * @param {string} darkThemeClass - The class representing the dark theme. Default is 'dark'.
 * @returns {boolean} - Returns true if dark mode is enabled, otherwise returns false. If the theme is not set in localStorage, it will use the system preference.
 */
export const isDarkMode = (
  key: string = DEFAULT_KEY,
  darkThemeClass: string = DARK_THEME,
): boolean => {
  const theme = localStorage.getItem(key);
  return (
    theme === darkThemeClass ||
    (!theme && window.matchMedia('(prefers-color-scheme: dark)').matches)
  );
};
const themeChanged = 'theme-changed';
/**
 * Loads the theme from localStorage and applies it to the document. If the theme is not set in localStorage,
 * it will use the system preference. If the system preference is not set, it will use the light theme.
 * The theme is stored in localStorage.
 *
 * @param key  - The key in localStorage where the theme is stored.
 * @param darkThemeClass - The class representing the dark theme. Default is 'dark'.
 * @param lightThemeClass - The class representing the light theme. Default is 'light'.
 * @returns void
 */
export const loadTheme = (
  key: string = DEFAULT_KEY,
  darkThemeClass: string = DARK_THEME,
  lightThemeClass: string = LIGHT_THEME,
): void => {
  const theme = localStorage.getItem(key);
  if (
    theme === darkThemeClass ||
    (!theme && window.matchMedia('(prefers-color-scheme: dark)').matches)
  ) {
    document.documentElement.classList.add(darkThemeClass);
    localStorage.setItem(key, darkThemeClass);
  } else {
    document.documentElement.classList.add(lightThemeClass);
    localStorage.setItem(key, lightThemeClass);
  }

  // Listen for theme change events
  document.addEventListener(themeChanged, ((event: CustomEvent<ThemeChangedEventDetail>) => {
    console.log('🚨 Theme changed', event.detail.isDark);
    const isDark = event.detail.isDark;
    document.documentElement.classList.toggle(darkThemeClass, isDark);
    localStorage.setItem(key, isDark ? darkThemeClass : lightThemeClass);
  }) as EventListener);
};

/**
 * Toggles the theme and stores the new value in localStorage.
 * @param {string} key - The key in localStorage where the theme is stored.
 * @param {string} darkThemeClass - The class representing the dark theme. Default is 'dark'.
 * @param lightThemeClass - The class representing the light theme. Default is 'light'.
 * @returns void
 */
export const toggleTheme = (
  key: string = DEFAULT_KEY,
  darkThemeClass: string = DARK_THEME,
  lightThemeClass: string = LIGHT_THEME,
): void => {
  const isDark = isDarkMode(key, darkThemeClass);
  document.documentElement.classList.toggle(darkThemeClass, !isDark);
  localStorage.setItem(key, !isDark ? darkThemeClass : lightThemeClass);
  // publish event to notify other components that the theme has changed (dark/light)
  document.dispatchEvent(new CustomEvent(themeChanged, { detail: { isDark: !isDark } }));
};

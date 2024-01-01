import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    '**/*/index.html',
    '**/*.{astro,html,js,jsx,md,mdx,svelte,ts,tsx,vue}',
  ],

  darkMode: 'class',

  theme: {
    extend: {
      colors: {
        primary: {
          50: '#FAF5FF', // Purple-01
          100: '#F3E7FF', // Purple-02
          200: '#E9D4FF', // Purple-03
          300: '#D8B2FF', // Purple-04
          400: '#C082FF', // Purple-05
          500: '#A751FB', // Purple-06
          600: '#922EEF', // Purple-07
          700: '#7D1ED2', // Purple-08
          800: '#6A1EAB', // Purple-09
          900: '#58198A', // Purple-10
          950: '#3B0566', // Purple-11
        },
        secondary: {
          50: '#F3F2FF', // Indigo-01
          100: '#E8E8FF', // Indigo-02
          200: '#D4D5FF', // Indigo-03
          300: '#B4B1FF', // Indigo-04
          400: '#8E85FF', // Indigo-05
          500: '#7A67FF', // Indigo-06
          600: '#5530F7', // Indigo-07
          700: '#481EE3', // Indigo-08
          800: '#3B18BF', // Indigo-09
          900: '#32169C', // Indigo-10
          950: '#1C0B6A', // Indigo-11
        },
        tertiary: {
          50: '#FAFAFA', // Gray-01
          100: '#F4F4F5', // Gray-02
          200: '#E4E4E7', // Gray-03
          300: '#D4D4D8', // Gray-04
          400: '#A1A1AA', // Gray-05
          500: '#71717A', // Gray-06
          600: '#52525B', // Gray-07
          700: '#3F3F46', // Gray-08
          800: '#27272A', // Gray-09
          900: '#18181B', // Gray-10
          950: '#09090B', // Gray-11
        },
      },
      fontFamily: {
        sans: [
          'Onest Variable',
          'Inter',
          'ui-sans-serif',
          'system-ui',
          '-apple-system',
          'system-ui',
          'Segoe UI',
          'Roboto',
          'Helvetica Neue',
          'Arial',
          'Noto Sans',
          'sans-serif',
          'Apple Color Emoji',
          'Segoe UI Emoji',
          'Segoe UI Symbol',
          'Noto Color Emoji',
        ],
        body: [
          'Onest Variable',
          'ui-sans-serif',
          'system-ui',
          '-apple-system',
          'system-ui',
          'Segoe UI',
          'Roboto',
          'Helvetica Neue',
          'Arial',
          'Noto Sans',
          'sans-serif',
          'Apple Color Emoji',
          'Segoe UI Emoji',
          'Segoe UI Symbol',
          'Noto Color Emoji',
        ],
        mono: [
          'ui-monospace',
          'SFMono-Regular',
          'Menlo',
          'Monaco',
          'Consolas',
          'Liberation Mono',
          'Courier New',
          'monospace',
        ],
      },
      transitionProperty: {
        width: 'width',
      },
      textDecoration: ['active'],
      minWidth: {
        kanban: '28rem',
      },
    },
  },

  safelist: [
    // In Markdown (README…)
    'justify-evenly',
    'overflow-hidden',
    'rounded-md',

    // From the Hugo Dashboard
    'w-64',
    'w-1/2',
    'rounded-l-lg',
    'rounded-r-lg',
    'bg-gray-200',
    'grid-cols-4',
    'grid-cols-7',
    'h-6',
    'leading-6',
    'h-9',
    'leading-9',
    'shadow-lg',
    'bg-opacity-50',
    'dark:bg-opacity-80',

    // For Astro one
    'grid',
  ],

  plugins: [
    require('tailwind-scrollbar')({nocompatible: true}),
    require('tailwindcss-animated')
  ],
};

export default config;

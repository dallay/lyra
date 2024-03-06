import {
	defineConfig,
	presetAttributify,
	presetIcons,
	presetTypography,
	presetUno,
	presetWebFonts,
	transformerDirectives,
} from 'unocss';
import { presetFlowbite } from '@julr/unocss-preset-flowbite';

export default defineConfig({
	presets: [
		presetUno(),
		presetFlowbite(),
		presetAttributify(),
		presetIcons({
			scale: 1.2,
		}),
		presetTypography(),
		presetWebFonts({
			provider: 'none',
			fonts: {
				script: 'Homemade Apple',
			},
		}),
	],
	transformers: [transformerDirectives({ enforce: 'pre' })],
	theme: {
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
	},
});

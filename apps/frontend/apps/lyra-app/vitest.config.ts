import { mergeConfig } from 'vite';
import { sharedVitestConfig } from '@lyra/config/vitest.config.shared';
import config from './vite.config';

export default mergeConfig(sharedVitestConfig(__dirname), config);

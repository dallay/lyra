import { UserConfig } from 'vite';

declare function sharedViteConfig(dirname: string): UserConfig | Promise<UserConfig>;

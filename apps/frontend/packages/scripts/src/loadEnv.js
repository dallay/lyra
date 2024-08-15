import path from 'path';
import { fileURLToPath } from 'url';
import fs from 'fs';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

/**
 * Finds the root directory of a monorepo by looking for specific files.
 *
 * @param {string} startDir - The directory to start the search from.
 * @returns {string} - The root directory of the monorepo.
 * @throws {Error} - If the monorepo root is not found.
 */
function findMonorepoRoot(startDir) {
  let currentDir = startDir;
  while (currentDir !== path.parse(currentDir).root) {
    if (fs.existsSync(path.join(currentDir, 'pnpm-workspace.yaml')) ||
      fs.existsSync(path.join(currentDir, '.git'))) {
      return currentDir;
    }
    currentDir = path.resolve(currentDir, '..');
  }
  throw new Error('Monorepo root not found');
}

const monorepoRoot = findMonorepoRoot(__dirname);
console.log('🟣 [monorepoRoot]:', monorepoRoot);

const envPath = path.resolve(monorepoRoot, '.env');

process.loadEnvFile(envPath);

const { API_URL, BACKEND_URL } = process.env;

console.log('🟣 [API_URL]:', API_URL);
console.log('🟣 [BACKEND_URL]:', BACKEND_URL);

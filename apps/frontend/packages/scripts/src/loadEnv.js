import path from 'node:path';
import { fileURLToPath } from 'node:url';
import fs from 'node:fs';

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
  const root = path.parse(currentDir).root;
  const checkFiles = ['.env', '.env.example', 'pnpm-workspace.yaml', '.git'];

  while (currentDir !== root) {
    if (checkFiles.some(file => fs.existsSync(path.join(currentDir, file)))) {
      return currentDir;
    }
    currentDir = path.resolve(currentDir, '..');
  }
  throw new Error('Monorepo root not found');
}

const monorepoRoot = findMonorepoRoot(__dirname);
console.log('🟣 [monorepoRoot]:', monorepoRoot);

const envPath = path.resolve(monorepoRoot, '.env');
// check if the .env file exists
if (fs.existsSync(envPath)) {
  process.loadEnvFile(envPath);
}

const { API_URL, BACKEND_URL } = process.env;

console.log('🟣 [API_URL]:', API_URL);
console.log('🟣 [BACKEND_URL]:', BACKEND_URL);

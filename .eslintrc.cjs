module.exports = {
  env: {
    node: true,
    es2022: true,
    browser: true,
  },
  extends: [
    'eslint:recommended',
    'plugin:astro/recommended',
    'plugin:astro/jsx-a11y-recommended',
    'prettier',
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
  },
  rules: {
    'no-mixed-spaces-and-tabs': 'off',
  },
  plugins: ['vue'],
  settings: {},
  overrides: [
    {
      files: ['*.vue'],
      parser: 'vue-eslint-parser',
      parserOptions: {
        parser: '@typescript-eslint/parser',
        ecmaVersion: 'latest',
        sourceType: 'module',
      },
      extends: [
        'eslint:recommended',
        'plugin:vue/vue3-recommended',
        '@vue/eslint-config-prettier',
        '@vue/eslint-config-typescript',
      ],
      rules: {
        'vue/multi-word-component-names': 'off',
        'vue/attribute-hyphenation': 'off',
        'vue/v-on-event-hyphenation': 'off',

        'vue/block-lang': ['error', { script: { lang: 'ts' } }],
        'vue/block-order': ['error', { order: ['script[setup]', 'template', 'style[scoped]'] }],
        'vue/block-tag-newline': [
          'error',
          { singleline: 'always', multiline: 'always', maxEmptyLines: 1 },
        ],
        'vue/component-api-style': ['error', ['script-setup']],
        'vue/component-name-in-template-casing': [
          'error',
          'PascalCase',
          { registeredComponentsOnly: true },
        ],
        'vue/component-options-name-casing': ['error', 'PascalCase'],
        'vue/custom-event-name-casing': ['error', 'camelCase'],
        'vue/define-emits-declaration': ['error', 'type-based'],
        'vue/define-macros-order': [
          'error',
          {
            order: ['defineOptions', 'defineModel', 'defineProps', 'defineEmits', 'defineSlots'],
            defineExposeLast: true,
          },
        ],
        'vue/define-props-declaration': ['error', 'type-based'],
        'vue/html-button-has-type': ['error', { button: true, submit: true, reset: true }],
      },
    },
    {
      files: ['*.astro'],
      parser: 'astro-eslint-parser',
      parserOptions: {
        parser: '@typescript-eslint/parser',
        extraFileExtensions: ['.astro'],
      },
      rules: {},
    },
    {
      files: ['*.ts', '*.mts'],
      parser: '@typescript-eslint/parser',
      extends: ['plugin:@typescript-eslint/recommended'],
      rules: {
        '@typescript-eslint/no-unused-vars': [
          'error',
          { argsIgnorePattern: '^_', destructuredArrayIgnorePattern: '^_' },
        ],
        '@typescript-eslint/no-non-null-assertion': 'off',
      },
    },
    {
      files: ['*.d.ts'],
      rules: {
        '@typescript-eslint/triple-slash-reference': 'off',
      },
    },
    {
      files: ['*.tsx'],
      parser: '@typescript-eslint/parser',
      plugins: ['@typescript-eslint'],
      parserOptions: {
        tsconfigRootDir: __dirname,
        project: ['./tsconfig.json'],
      },
      extends: [
        'eslint:recommended',
        'plugin:@typescript-eslint/recommended',
        'plugin:@typescript-eslint/recommended-requiring-type-checking',
      ],
      rules: {
        '@typescript-eslint/no-unused-vars': [
          'error',
          { argsIgnorePattern: '^_', destructuredArrayIgnorePattern: '^_' },
        ],
        '@typescript-eslint/no-non-null-assertion': 'off',
      },
    },
    {
      // Define the configuration for `<script>` tag.
      // Script in `<script>` is assigned a virtual file name with the `.js` extension.
      files: ['**/*.astro/*.js', '*.astro/*.js'],
      parser: '@typescript-eslint/parser',
    },
  ],
};

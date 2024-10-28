module.exports = {
  overrides: [
    {
      files: ['**/*.scss'],
      customSyntax: 'postcss-scss',
    },
  ],
  extends: ['stylelint-config-standard'],
  rules: {
    "selector-pseudo-class-no-unknown": [
      true,
      {
        "ignorePseudoClasses": ["global"]
      }
    ],
    'at-rule-no-unknown': [
      true,
      {
        ignoreAtRules: ['tailwind', 'unocss', 'apply', 'variants', 'responsive', 'screen', 'ProseMirror', 'use'],
      },
    ],
    'selector-class-pattern': null,
  },
};

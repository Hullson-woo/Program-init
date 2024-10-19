import globals from 'globals'
import pluginJs from '@eslint/js'
import tseslint from 'typescript-eslint'
import pluginVue from 'eslint-plugin-vue'
import pluginPrettierRecommendedConfigs from 'eslint-plugin-prettier/recommended'
import parserVue from 'vue-eslint-parser'

export default [
  // { files: ['**/*.{js,mjs,cjs,ts,vue}'] },
  // eslint 默认推荐规则
  pluginJs.configs.recommended,
  // ts默认推荐规则
  ...tseslint.configs.recommended,
  // vue3基础推荐规则
  ...pluginVue.configs['flat/essential'],
  // prettier默认推荐规则
  pluginPrettierRecommendedConfigs,
  {
    languageOptions: {
      globals: {
        ...globals.browser,
        ...globals.es2020,
        ...globals.node,
      },
      ecmaVersion: 2020,
      parser: parserVue,
      parserOptions: {
        parser: tseslint.parser,
      },
    },
    // 自定义eslint规则
    rules: {
      'vue/multi-word-component-names': 'off',
    },
  },
  { files: ['**/*.vue'], languageOptions: { parserOptions: { parser: tseslint.parser } } },
]

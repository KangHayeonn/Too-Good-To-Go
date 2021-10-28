/* eslint-disable */
//emotion과 typescript를 함께 사용할 시 문제 해결
const {
  useBabelRc,
  removeModuleScopePlugin,
  override,
} = require("customize-cra");
module.exports = override(useBabelRc(), removeModuleScopePlugin());

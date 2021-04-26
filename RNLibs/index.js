import React from 'react';
import { AppRegistry } from 'react-native';
import App from './modules/SmartPlug/App';

console.log('本地调试入口')
AppRegistry.registerComponent(
  'MyReactNativeApp',
  () => App
);
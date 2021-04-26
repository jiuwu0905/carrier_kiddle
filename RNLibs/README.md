#### 项目说明

#### Deprecated 不再维护这个目录下的js代码，已迁移到[http://192.168.2.3/Akeeta_OEM_App/akeeta_rn.git](http://192.168.2.3/Akeeta_OEM_App/akeeta_rn.git)

RN项目基于`react hooks`、`immer.js`、`react-navigation`实现快速开发框架。

#### 安装相关依赖

```
npm i // 或者 npm install
```
也可以使用`yarn`, 如果没有安装`yarn`, 请先安装:

```
npm install -g yarn
```

使用`yarn`安装

```
yarn
```

#### 依赖库

```
"dependencies": {
    "@react-native-community/hooks": "2.6.0",
    "@react-native-community/masked-view": "0.1.10",
    "@react-navigation/native": "5.7.1",
    "@react-navigation/stack": "5.7.1",
    "beeshell": "2.0.11",
    "i18n-js": "3.7.1",
    "immer": "7.0.7",
    "lottie-react-native": "3.3.2",
    "react": "16.13.1",
    "react-native": "0.61.5",
    "react-native-gesture-handler": "1.6.1",
    "react-native-linear-gradient": "2.5.6",
    "react-native-reanimated": "1.9.0",
    "react-native-safe-area-context": "3.1.1",
    "react-native-screens": "2.9.0",
    "react-native-svg": "12.0.3",
    "react-native-vector-icons": "6.6.0",
    "use-immer": "0.4.1"
  }
```

##### 目录说明

```
|- common // 公共组件和公共页面
|	|- assets   // 公共图片等资源
|	|- components  // 组件
|	|- languages  // 公共多语言配置
|   	|- zh.json
|		|- en.json
|	|- pages   // 页面
|	|- router.js  // 公共页面跳转路由
|	|- index.js   // 入口
|- core  // 接口等封装
|	|- i18n
|		|- i18n.js // 多语言配置
|		|- index.js // 入口
|		|- LocaleProvider.js // 多语言 hooks
|	|- AkeetaJS.js // 原生桥接接口
|	|- AppNavigation.js // 应用路由、多语言配置入口
|	|- AssetsManager.js // 图片资源统一管理
|	|- DeviceProvider.js // 通过hooks统一管理了控制面板需要的相关数据，包括设备信息、
|	|					 // 物模型、定时列表等数据, 通过immer.js来保证数据不可变
|	|- index.js //入口，提供核心方法，es5方式处理
|	|- StyleSheet.js // 封装react-native的StyleSheet，
|	|				 // 可以针对android或ios单独做样式处理
|	|- useApi.js // hooks 方式对AkeetaJS.js进行二次封装
|	|- useDeviceNotification.js // hooks 方式接收推送设备信息
|	|- usePropertyNotification.js // hooks 方式接收推送物模型变化信息
|- modules  // 面板
|	|- Light  // 模块名称
|		|- assets // 图片等资源
|		|- components // 组件
|		|- languages // 模块自己的多语言设置
|		|- pages // 页面
|		|- index.js // 整个模块打包入口
|		|- module.json // 模块配置文件
|		|- router.js  // 路由配置文件
|- router.js // 本地调试路由配置
|- index.js // 本地调试入口
|- package.json // 依赖配置
```

#### modules目录下的模板代码

##### `moduls.json`: 模块配置信息，会一起打到zip包中，原生打开控制页面会获取这个文件里面的信息

```
{
  "name": "Light",
  "displayName": "Light",
  "version": "20200818",
}
```

##### `App.js`：入口文件

```
import React from 'react';
import { createApp, AssetsManager } from '@/core';
import router from './router';
// 如果使用common里面的资源，要加入这行代码
import '@/common/assets';
// 如果统一修改样式，要加入这行代码
import './customTheme';

// 注册你要使用的图片资源，在这里统一注册
// 使用时: AssetsManager.load('SmartPlug. switch_off')就可获得图片
AssetsManager.register("SmartPlug", {
  "switch_off": require('./assets/switch-off.png'),
  "logo_off": require('./assets/logo-off.png')
});

// 创建你的app
export default createApp({
  initLanguages: require('./languages').default
})(router);
```

##### `index.js` 打包入口，请不要修改这个文件中的代码

```
import { AppRegistry } from 'react-native';
import { name as appName } from './module.json';
import App from "./App";
AppRegistry.registerComponent(appName, () => App);
```

##### `router.js` 路由配置

```
import React from 'react';
import PlugBoard from './pages/PlugBoard';

export default [
  {
    name: 'PlugBoard',
    component: PlugBoard
  }
];
```

##### `customTheme.js`

```
// 可有可无
import { useTheme } from 'beeshell/dist/common/styles/variables'

// 自定义主题变量
const customVariables = {
  mtdGrayBase: '#fff',
  mtdFontSizeL: 17
}

// 自定义主题变量与默认主题变量 merge，并返回新的主题变量
const ret = useTheme(customVariables)
export default ret
```

#### `core`目录下提供的方法说明

##### 使用：

```
import { 
	createApp, 
	AssetsManager, 
	createStyleSheet, 
	useApi, 
	useDeviceNotification, 
	usePropertyNotification, 
	useLocaleContext,
	useDeviceContext
} from '@/core';
```

这里提供了一个小技巧，如果不想写过多的`../../../core`, 可以直接用`@/core`, `@/`直接代表了`RNLibs`这个目录，但是有一个不好的体验时，不直跳转到相关文件中

##### `createApp`: 通过高阶函数的方式实现了整个RN页面的初始化

```
import { createApp } from '@/core';

const params = {
	initialRouteName: null, // 指定要启动的页面
	providers: [], // hooks的context provider，详见 AppNavigation.js
	initLanguages: {} // 多言语，默认会加载common中的
};

const routers = [
	{
		name: 'PlugBoard',
		component: PlugBoard // 你的RN页面
	}
]

createApp(params)(routers);


```

##### `createStyleSheet`用法：

```
import React from 'react';
import {View} from 'react-native';
import { createStyleSheet } from '@/core';

export default () => (
	<View style={styles.wrapper}/>
);

const styles = createStyleSheet({
	wrapper: {
		flex: 1,
		android: {backgroundColor: 'red'}, // android显示红色背景
		ios: {backgroundColor: 'blue'} // ios显示蓝色背景
	}
});

```

##### `useApi`用法

```
import React from 'react';
import {View, Button} from 'react-native';
import { useApi } from '@/core';

export default ({deviceId}) => {
	const [loading, error, api] = useApi(deviceId);
	const onPress = () => {
		const params = { SwitchMultiple: isOff ? "0" : '1' };
    	api.modifyProperties(params);
	}
	return (
		<View style={styles.wrapper}>
			{loading ? <显示loading> : null}
			<Button title="api test" onPress={onPress}/>
		</View>
	);
}

const styles = createStyleSheet({
	wrapper: {
		flex: 1,
		android: {backgroundColor: 'red'}, // android显示红色背景
		ios: {backgroundColor: 'blue'} // ios显示蓝色背景
	}
});

```

##### `useLocaleContext`用法

```
import React from 'react';
import {View, Text} from 'react-native';
import { useLocaleContext } from '@/core';

export default ({deviceId}) => {
	const {t} = useLocaleContext();
	return (
		<View style={styles.wrapper}>
			<Text>{t('common.title')}</Text>
		</View>
	);
}

const styles = createStyleSheet({
	wrapper: {
		flex: 1,
		android: {backgroundColor: 'red'}, // android显示红色背景
		ios: {backgroundColor: 'blue'} // ios显示蓝色背景
	}
});

```

##### `useDeviceContext`用法

```
import React from 'react';
import {View, Text} from 'react-native';
import { useDeviceContext } from '@/core';

export default ({deviceId}) => {
	const {state} = useDeviceContext();
	const { deviceInfo, deviceProperties, deviceTimeList } = state;
	return (
		<View style={styles.wrapper}>
		</View>
	);
}

const styles = createStyleSheet({
	wrapper: {
		flex: 1,
		android: {backgroundColor: 'red'}, // android显示红色背景
		ios: {backgroundColor: 'blue'} // ios显示蓝色背景
	}
});
```

##### `pages`目录下的js文件根组件都要为`Screen`

```
import React from 'react';
import {View, Text} from 'react-native';
import Screen from '@/common/components/Screen'

export default ({deviceId}) => {
	const {state} = useDeviceContext();
	const { deviceInfo, deviceProperties, deviceTimeList } = state;
	return (
		<Screen style={styles.wrapper}>
		</Screen>
	);
}

const styles = createStyleSheet({
	wrapper: {
		flex: 1,
		android: {backgroundColor: 'red'}, // android显示红色背景
		ios: {backgroundColor: 'blue'} // ios显示蓝色背景
	}
});
```

参数说明

| 参数                  | 类型     | 是否必填 | 说明                                                 |
| --------------------- | -------- | -------- | ---------------------------------------------------- |
| linearGradient        | boolean  | 否       | 渐变背景                                             |
| linearGradientOptions | array    | 否       | 渐变背景颜色，<br />`linearGradient`为`true`时才有效 |
| title                 | string   | 否       | 标题，默认空                                         |
| headerOptions         | object   | 否       | 顶部导航栏，详见beeshell `NavigationBar`说明         |
| onPressBack           | function | 否       | 返回点击事件                                         |
| loading               | boolean  | 否       | 是否显示loding                                       |
| isDarkTheme           | boolean  | 否       | 是否显示深色loading,默认False                        |
| online                | boolean  | 否       | 设备是否在线                                         |



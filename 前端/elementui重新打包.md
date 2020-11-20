 修改了element-ui组件后，想打包构建

执行构建的时候提示

 

```
Replace Autoprefixer browsers option to Browserslist config.
Use browserslist key in package.json or .browserslistrc file.

Using browsers option cause some error. Browserslist config
can be used for Babel, Autoprefixer, postcss-normalize and other tools.

If you really need to use option, rename it to overrideBrowserslist.
Learn more at:
[https://github.com/browserslist/browserslist#readme](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fbrowserslist%2Fbrowserslist%23readme)
[https://twitter.com/browserslist](https://links.jianshu.com/go?to=https%3A%2F%2Ftwitter.com%2Fbrowserslist)
```

![img](E:\learn\git\repository\笔记\java-note\前端\img\20200619085831658.png)

```
 "dist": "npm run clean && npm run build:file && npm run lint && webpack --config build/webpack.conf.js && webpack --config build/webpack.common.js && webpack --config build/webpack.component.js && npm run build:utils && npm run build:umd && npm run build:theme",
```

检查了下，前面没有出错，执行到build:them才出错

应该是gulp构建CSS的时候适应浏览器出错了，网上有人说，版本过高了。

打开修改package.json

autoprefixer版本修改完，5.0.0

```
"autoprefixer": "^5.0.0",
```

执行

```
npm install
```

修改gulpfile.js 12行

把browsers 改为overrideBrowserslist

```
overrideBrowserslist: ['ie > 9', 'last 2 versions'],
browsers: ['ie > 9', 'last 2 versions'],
```

再次执行dist后顺利通过了。

把lib、package文件夹复制到生产环境替换
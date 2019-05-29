-dev 开发环境  --save保存到json文件

webpack安装失败

1、检查node版本

2、网络问题


1、初始化脚本
npm n init

2、npm install --save-dev webpack

项目目录 -- webpack demo

1、src  src/entry.js  入口文件
2、dist  dist/index.html  编译后文件
webpack src/entry.js dist/bundle.js  编译命令


3、配置文件： 出口与入口
webpack.config.js   --直接webpack打包
const path = require('path')
module.exports = {
	entry:{
		entry:'./src/entry.js',
		entry2:'./src/entry2.js'
	}, //入口
	output:{
		path:path.resolve(__dirname,'dist'),//node语法
		filename:'bundle.js'//单出口
		filename:'[name].js' //多出口(name  表示与入口文件一致)
	},//出口
	module:{},//配置模块，主要是解析CSS和图片转换压缩等功能。
	plugins:[],//配置插件，根据你的需要配置不同功能的插件。
	devServer:{} //配置开发服务功能，后期我们会详细讲解。
	
}


4、配置文件： 服务和热更新
webpack-dev-server     npm install webpack-dev-server –save-dev

devServer:{
        //设置基本目录结构
        contentBase:path.resolve(__dirname,'dist'),// 配置服务器基本运行路径，用于找到程序打包地址。
        //服务器的IP地址，可以使用IP也可以使用localhost
        host:'localhost',
        //服务端压缩是否开启[服务器端压缩选型，一般设置为开启，如果你对服务器压缩感兴趣，可以自行学习。]
        compress:true,
        //配置服务端口号[服务运行端口，建议不使用80，很容易被占用，这里使用了1717.]
        port:1717
    }

/package.json
"scripts": {
    "server":"webpack-dev-server"
 },

npm run server  启动服务

第05节：模块：CSS文件打包
注意：所有的Loaders都需要在npm中单独进行安装，并在webpack.config.js里进行配置。下面我们对Loaders的配置型简单梳理一下。

test：用于匹配处理文件的扩展名的表达式，这个选项是必须进行配置的；
use：loader名称，就是你要使用模块的名称，这个选项也必须进行配置，否则报错；
include/exclude:手动添加必须处理的文件（文件夹）或屏蔽不需要处理的文件（文件夹）（可选）；
query：为loaders提供额外的设置选项（可选）。

处理css需要两个loader  
style-loader:它是用来处理css文件中的url()等   npm install style-loader --save-dev
css-loader：它是用来将css插入到页面的style标签  npm n install --save-dev css-loader

修改webpack.config.js中module属性中的配置代码如下：
rules: [
            {
              test: /\.css$/,
              use: [ 'style-loader', 'css-loader' ]
            }
          ]

loader的三种写法：
第一种写法：直接用use。

module:{
        rules:[
            {
                test:/\.css$/,
                use:['style-loader','css-loader']
            }
        ]
    },

第二种写法：把use换成loader。

module:{
        rules:[
            {
                test:/\.css$/,
                loader:['style-loader','css-loader']
            }
        ]
    },

第三种写法：用use+loader的写法：

module:{
        rules:[
            {
                test:/\.css$/,
                use: [
                    {
                        loader: "style-loader"
                    }, {
                        loader: "css-loader"
                    }
                ]
            }
        ]
    },

第06节：插件配置：配置JS压缩
uglifyjs-webpack-plugin  (webpack默认已经集成)

const uglify = `require`('uglifyjs-webpack-plugin');

 plugins:[
        new uglify()
    ],

第07节：插件配置：HTML文件的发布

devServer和JS压缩的冲突

上节课学习了JS压缩，在视频中我使用了webpack进行打包，而没有使用npm run server 进行预览，也就是说没有启用devServer里的配置。那有些小伙伴在学习完视频后，在终端中输入了npm run server进行了预览，发现终端中报错了。

要弄明白这个问题，我们先要弄清楚什么是开发环境，什么是生产环境。开发环境中是基本不会对js进行压缩的，在开发预览时我们需要明确的报错行数和错误信息，所以完全没有必要压缩JavasScript代码。而生产环境中才会压缩JS代码，用于加快程序的工作效率。devServer用于开发环境，而压缩JS用于生产环境，在开发环境中作生产环境的事情所以Webpack设置了冲突报错。

在实际开发中，webpack配置文件是分开的，开发环境一个文件，生产环境一个文件。

const htmlPlugin= `require`('html-webpack-plugin');
npm install --save-dev html-webpack-plugin
最后在webpack.config.js里的plugins里进行插件配置，配置代码如下。

 new htmlPlugin({
            minify:{
                removeAttributeQuotes:true //是对html文件进行压缩，removeAttrubuteQuotes是却掉属性的双引号。
            },
            hash:true, //为了开发中js有缓存效果，所以加入hash，这样可以有效避免缓存JS。
            template:'./src/index.html' //是要打包的html模版路径和文件名称。
        })


第08节：图片迈坑：CSS中的图片处理
npm install --save-dev file-loader url-loader
file-loader：解决引用路径的问题，拿background样式用url引入背景图来说，我们都知道，webpack最终会将各个模块打包成一个文件，因此我们样式中的url路径是相对入口html页面的，而不是相对于原始css文件所在的路径的。这就会导致图片引入失败。这个问题是用file-loader解决的，file-loader可以解析项目中的url引入（不仅限于css），根据我们的配置，将图片拷贝到相应的路径，再根据我们的配置，修改打包后文件引用路径，使之指向正确的文件。

url-loader：如果图片较多，会发很多http请求，会降低页面性能。这个问题可以通过url-loader解决。url-loader会将引入的图片编码，生成dataURl。相当于把图片数据翻译成一串字符。再把这串字符打包到文件中，最终只需要引入这个文件就能访问图片了。当然，如果图片较大，编码会消耗性能。因此url-loader提供了一个limit参数，小于limit字节的文件会被转为DataURl，大于limit的还会使用file-loader进行copy。

module:{
        rules: [
            {
              test: /\.css$/,
              use: [ 'style-loader', 'css-loader' ]
            },{
               test:/\.(png|jpg|gif)/ ,
               use:[{
                   loader:'url-loader',
                   options:{
                       limit:500000
                   }
               }]
            }
          ]
    },

test:/.(png|jpg|gif)/是匹配图片文件后缀名称。
use：是指定使用的loader和loader的配置参数。
limit：是把小于500000B的文件打成Base64的格式，写入JS。

为什么只使用了url-loader

有的小伙伴会发现我们并没有在webpack.config.js中使用file-loader，但是依然打包成功了。我们需要了解file-loader和url-loader的关系。url-loader和file-loader是什么关系呢？简答地说，url-loader封装了file-loader。url-loader不依赖于file-loader，即使用url-loader时，只需要安装url-loader即可，不需要安装file-loader，因为url-loader内置了file-loader。通过上面的介绍，我们可以看到，url-loader工作分两种情况：

1.文件大小小于limit参数，url-loader将会把文件转为DataURL（Base64格式）；

2.文件大小大于limit，url-loader会调用file-loader进行处理，参数也会直接传给file-loader。

也就是说，其实我们只安装一个url-loader就可以了。但是为了以后的操作方便，我们这里就顺便安装上file-loader。

第09节：图片迈坑：CSS分离与图片路径处理

CSS分离:extract-text-webpack-plugin

有些简单的交互页面中，你的JavasScript页面代码会非常少，而大部分代码都在CSS中，这时候项目组长会要求把CSS单独提取出来，方便以后更改。遇到这个需求你不要惊慌，已经有大神为我们准备好了对象的插件（plugin）。

extract-text-webpack-plugin

alt
这个插件就可以完美的解决我们提取CSS的需求，但是webpack官方其实并不建议这样作，他们认为CSS就应该打包到JavasScript当中以减少http的请求数。但现实中的需求往往不是我们前端能控制的，有些需求是我们不能控制的，分离CSS就是这样一个既合理由不合理的需求。

安装：录制课程时的版本是3.0.0版本，直接使用npm install 就可以安装。

npm install --save-dev extract-text-webpack-plugin
引入：安装完成后，需要先用require引入。

const extractTextPlugin = `require`("extract-text-webpack-plugin");
设置Plugins：引入成功后需要在plugins属性中进行配置。这里只要new一下这个对象就可以了。

new extractTextPlugin("/css/index.css")
这里的/css/index.css是分离后的路径位置。这部配置完成后，包装代码：还要修改原来我们的style-loader和css-loader。

修改代码如下。

module:{
        rules: [
            {
              test: /\.css$/,
              use: extractTextPlugin.extract({
                fallback: "style-loader",
                use: "css-loader"
              })
            },{
               test:/\.(png|jpg|gif)/ ,
               use:[{
                   loader:'url-loader',
                   options:{
                       limit:500000
                   }
               }]
            }
          ]
    },

作完上边这四部后，就可以使用webpack进行打包了。

图片路径问题：

利用extract-text-webpack-plugin插件很轻松的就把CSS文件分离了出来，但是CSS路径并不正确，很多小伙伴就在这里搞个几天还是没有头绪，网上也给出了很多的解决方案，我觉的最好的解决方案是使用publicPath解决，我也一直在用。

publicPath：是在webpack.config.js文件的output选项中，主要作用就是处理静态文件路径的。

在处理前，我们在webpack.config.js 上方声明一个对象，叫website。

var website ={
    publicPath:"http://192.168.1.108:1717/"
}
注意，这里的IP和端口，是你本机的ip或者是你devServer配置的IP和端口。

然后在output选项中引用这个对象的publicPath属性。

//出口文件的配置项
    output:{
        //输出的路径，用了Node语法
        path:path.resolve(__dirname,'dist'),
        //输出的文件名称
        filename:'[name].js',
        publicPath:website.publicPath
    },

配置完成后，你再使用webpack命令进行打包，你会发现原来的相对路径改为了绝对路径，这样来讲速度更快。

第10节：图片迈坑：处理HTML中的图片

html-withimg-loader

html-withimg-loader就是我们今天的重点了，这个插件并不是很火，也是我个人喜欢的一个小loader。解决的问题就是在hmtl文件中引入标签的问题。

安装：

npm install html-withimg-loader --save
配置loader

webpack.config.js

{
    test: /\.(htm|html)$/i,
     use:[ 'html-withimg-loader'] 
}

然后在终端中可以进行打包了。你会发现images被很好的打包了。并且路径也完全正确。

第11节：CSS进阶：Less文件的打包和分离

打包Less文件

安装:

要使用Less，我们要首先安装Less的服务，当然也是用npm来进行安装。

npm install --save-dev less
还需要安装Less-loader用来打包使用。

npm n install --save-dev less-loader
写loader配置：

安装好后，需要在webpack.config.js里编写loader配置，当然要想正确解析成CSS，还是需要style-loader和css-loader的帮助，但是这两个loader前边已经讲过了，所以在这里就不重复了，如果你还对这两个loader不熟悉，那自行回去补前边的第五节吧。

webpack.config.js

{
    test: /\.less$/,
    use: [{
           loader: "style-loader" // creates style nodes from JS strings
        }, {
            loader: "css-loader" // translates CSS into CommonJS
        , {
            loader: "less-loader" // compiles Less to CSS
        }]
}
css
编写一个less文件

现在webpack的配置好了，我们还需要编写一个less文件，这里明文为black.less.里边只做一件是就是把一个层的背景设置成黑色。

black.less

@base :#000;
#gogo{
    width:300px;
    height:300px;
    background-color:@base;
}
less
这里#gogo是层的ID名称。@base是我们设置的变量名称。

引入到我们entery.js文件中

import less from './css/black.less';
这样我们就可以把less文件进行打包了。我们可以使用webpack命令打包试一试。

把Lees文件分离。

我们之前讲了extract-text-webpack-plugin这个插件，想把Less文件分离出来的方法跟这个几乎一样，之前的我们在第09节中讲过，这里我们就只讲less的loader配置方法。（此处建议收看视频）

{
            test: /\.less$/,
            use: extractTextPlugin.extract({
                use: [{
                    loader: "css-loader"
                }, {
                    loader: "less-loader"
                }],
                // use style-loader in development
                fallback: "style-loader"
            })
 }

配置好后，你会发现less被分离到了index.css文件里。

第12节：CSS进阶：SASS文件的打包和分离

安装SASS打包的loader
在项目目录下用npm安装两个包。
node-sass：npm n install --save-dev node-sass
sass-loader：npm install --save-dev sass-loader

{
	test: /\.scss$/,
	use: [{
		loader: "style-loader" // creates style nodes from JS strings
	}, {
		loader: "css-loader" // translates CSS into CommonJS
	}, {
		loader: "sass-loader" // compiles Sass to CSS
	}]
}

{
	test: /\.scss$/,
	use: extractTextPlugin.extract({
		use: [{
			loader: "css-loader"
		}, {
			loader: "sass-loader"
		}],
		// use style-loader in development
		fallback: "style-loader"
	})
 }
 
 第13节：CSS进阶：自动处理CSS3属性前缀
 什么是属性前缀

我们先来看一下代码：

-webkit-transform: rotate(45deg);
        transform: rotate(45deg);
为了浏览器的兼容性，有时候我们必须加入-webkit,-ms,-o,-moz这些前缀。目的就是让我们写的页面在每个浏览器中都可以顺利运行。

PostCSS

PostCSS是一个CSS的处理平台，它可以帮助你的CSS实现更多的功能，但是今天我们就通过其中的一个加前缀的功能，初步了解一下PostCSS。

安装

需要安装两个包postcss-loader 和autoprefixer（自动添加前缀的插件）

npm install --save-dev postcss-loader autoprefixer
postcss.config.js

postCSS推荐在项目根目录（和webpack.config.js同级），建立一个postcss.config.js文件。

postcss.config.js

module.exports = {
    plugins: [
        require('autoprefixer')
    ]
}

这就是对postCSS一个简单的配置，引入了autoprefixer插件。让postCSS拥有添加前缀的能力，它会根据 can i use 来增加相应的css3属性前缀。

编写loader

对postcss.config.js配置完成后，我们还需要编写我们的loader配置。

{
      test: /\.css$/,
      use: [
            {
              loader: "style-loader"
            }, {
              loader: "css-loader",
              options: {
                 modules: true
              }
            }, {
              loader: "postcss-loader"
            }
      ]
}

提取CSS
配置提取CSS的loader配置.

{
    test: /\.css$/,
    use: extractTextPlugin.extract({
        fallback: 'style-loader',
        use: [
            { loader: 'css-loader', options: { importLoaders: 1 } },
            'postcss-loader'
        ]
    })
}


第14节：CSS进阶：消除未使用的CSS
PurifyCSS
使用PurifyCSS可以大大减少CSS冗余，比如我们经常使用的BootStrap(140KB)就可以减少到只有35KB大小。这在实际开发当中是非常有用的。

安装PurifyCSS-webpack
从名字你就可以看出这是一个插件，而不是loader。所以这个需要安装还需要引入。 PurifyCSS-webpack要以来于purify-css这个包，
所以这两个都需要安装。

npmn  i -D purifycss-webpack purify-css
这里的-D代表的是–save-dev ,只是一个简写。

引入glob
因为我们需要同步检查html模板，所以我们需要引入node的glob对象使用。在webpack.config.js文件头部引入glob。
const glob = `require`('glob');
引入purifycss-webpack
同样在webpack.config.js文件头部引入purifycss-webpack
const PurifyCSSPlugin = `require`("purifycss-webpack");
配置plugins

引入完成后我们需要在webpack.config.js里配置plugins。代码如下，重点看标黄部分。

plugins:[
    //new uglify() 
    new htmlPlugin({
        minify:{
            removeAttrubuteQuotes:true
        },
        hash:true,
        template:'./src/index.html'
    }),
    new extractTextPlugin("css/index.css"),
    new PurifyCSSPlugin({
        // Give paths to parse for rules. These should be absolute!
        paths: glob.sync(path.join(__dirname, 'src/*.html')),
        })
]

这里配置了一个paths，主要是需找html模板，purifycss根据这个配置会遍历你的文件，查找哪些css被使用了。

注意：使用这个插件必须配合extract-text-webpack-plugin这个插件，这个插件在前边的课程已经讲解过了。如果你还不会请自学一下。

配置好上边的代码，我们可以故意在css文件里写一些用不到的属性，然后用webpack打包，你会发现没用的CSS已经自动给你删除掉了。在工作中记得一定要配置这个plugins，因为这决定你代码的质量，非常有用。
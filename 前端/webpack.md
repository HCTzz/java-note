-dev ��������  --save���浽json�ļ�

webpack��װʧ��

1�����node�汾

2����������


1����ʼ���ű�
npm n init

2��npm install --save-dev webpack

��ĿĿ¼ -- webpack demo

1��src  src/entry.js  ����ļ�
2��dist  dist/index.html  ������ļ�
webpack src/entry.js dist/bundle.js  ��������


3�������ļ��� ���������
webpack.config.js   --ֱ��webpack���
const path = require('path')
module.exports = {
	entry:{
		entry:'./src/entry.js',
		entry2:'./src/entry2.js'
	}, //���
	output:{
		path:path.resolve(__dirname,'dist'),//node�﷨
		filename:'bundle.js'//������
		filename:'[name].js' //�����(name  ��ʾ������ļ�һ��)
	},//����
	module:{},//����ģ�飬��Ҫ�ǽ���CSS��ͼƬת��ѹ���ȹ��ܡ�
	plugins:[],//���ò�������������Ҫ���ò�ͬ���ܵĲ����
	devServer:{} //���ÿ��������ܣ��������ǻ���ϸ���⡣
	
}


4�������ļ��� ������ȸ���
webpack-dev-server     npm install webpack-dev-server �Csave-dev

devServer:{
        //���û���Ŀ¼�ṹ
        contentBase:path.resolve(__dirname,'dist'),// ���÷�������������·���������ҵ���������ַ��
        //��������IP��ַ������ʹ��IPҲ����ʹ��localhost
        host:'localhost',
        //�����ѹ���Ƿ���[��������ѹ��ѡ�ͣ�һ������Ϊ�����������Է�����ѹ������Ȥ����������ѧϰ��]
        compress:true,
        //���÷���˿ں�[�������ж˿ڣ����鲻ʹ��80�������ױ�ռ�ã�����ʹ����1717.]
        port:1717
    }

/package.json
"scripts": {
    "server":"webpack-dev-server"
 },

npm run server  ��������

��05�ڣ�ģ�飺CSS�ļ����
ע�⣺���е�Loaders����Ҫ��npm�е������а�װ������webpack.config.js��������á��������Ƕ�Loaders�������ͼ�����һ�¡�

test������ƥ�䴦���ļ�����չ���ı��ʽ�����ѡ���Ǳ���������õģ�
use��loader���ƣ�������Ҫʹ��ģ������ƣ����ѡ��Ҳ����������ã����򱨴�
include/exclude:�ֶ���ӱ��봦����ļ����ļ��У������β���Ҫ������ļ����ļ��У�����ѡ����
query��Ϊloaders�ṩ���������ѡ���ѡ����

����css��Ҫ����loader  
style-loader:������������css�ļ��е�url()��   npm install style-loader --save-dev
css-loader������������css���뵽ҳ���style��ǩ  npm n install --save-dev css-loader

�޸�webpack.config.js��module�����е����ô������£�
rules: [
            {
              test: /\.css$/,
              use: [ 'style-loader', 'css-loader' ]
            }
          ]

loader������д����
��һ��д����ֱ����use��

module:{
        rules:[
            {
                test:/\.css$/,
                use:['style-loader','css-loader']
            }
        ]
    },

�ڶ���д������use����loader��

module:{
        rules:[
            {
                test:/\.css$/,
                loader:['style-loader','css-loader']
            }
        ]
    },

������д������use+loader��д����

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

��06�ڣ�������ã�����JSѹ��
uglifyjs-webpack-plugin  (webpackĬ���Ѿ�����)

const uglify = `require`('uglifyjs-webpack-plugin');

 plugins:[
        new uglify()
    ],

��07�ڣ�������ã�HTML�ļ��ķ���

devServer��JSѹ���ĳ�ͻ

�Ͻڿ�ѧϰ��JSѹ��������Ƶ����ʹ����webpack���д������û��ʹ��npm run server ����Ԥ����Ҳ����˵û������devServer������á�����ЩС�����ѧϰ����Ƶ�����ն���������npm run server������Ԥ���������ն��б����ˡ�

ҪŪ����������⣬������ҪŪ���ʲô�ǿ���������ʲô�����������������������ǻ��������js����ѹ���ģ��ڿ���Ԥ��ʱ������Ҫ��ȷ�ı��������ʹ�����Ϣ��������ȫû�б�Ҫѹ��JavasScript���롣�����������вŻ�ѹ��JS���룬���ڼӿ����Ĺ���Ч�ʡ�devServer���ڿ�����������ѹ��JS���������������ڿ�����������������������������Webpack�����˳�ͻ����

��ʵ�ʿ����У�webpack�����ļ��Ƿֿ��ģ���������һ���ļ�����������һ���ļ���

const htmlPlugin= `require`('html-webpack-plugin');
npm install --save-dev html-webpack-plugin
�����webpack.config.js���plugins����в�����ã����ô������¡�

 new htmlPlugin({
            minify:{
                removeAttributeQuotes:true //�Ƕ�html�ļ�����ѹ����removeAttrubuteQuotes��ȴ�����Ե�˫���š�
            },
            hash:true, //Ϊ�˿�����js�л���Ч�������Լ���hash������������Ч���⻺��JS��
            template:'./src/index.html' //��Ҫ�����htmlģ��·�����ļ����ơ�
        })


��08�ڣ�ͼƬ���ӣ�CSS�е�ͼƬ����
npm install --save-dev file-loader url-loader
file-loader���������·�������⣬��background��ʽ��url���뱳��ͼ��˵�����Ƕ�֪����webpack���ջὫ����ģ������һ���ļ������������ʽ�е�url·����������htmlҳ��ģ������������ԭʼcss�ļ����ڵ�·���ġ���ͻᵼ��ͼƬ����ʧ�ܡ������������file-loader����ģ�file-loader���Խ�����Ŀ�е�url���루��������css�����������ǵ����ã���ͼƬ��������Ӧ��·�����ٸ������ǵ����ã��޸Ĵ�����ļ�����·����ʹָ֮����ȷ���ļ���

url-loader�����ͼƬ�϶࣬�ᷢ�ܶ�http���󣬻ή��ҳ�����ܡ�����������ͨ��url-loader�����url-loader�Ὣ�����ͼƬ���룬����dataURl���൱�ڰ�ͼƬ���ݷ����һ���ַ����ٰ��⴮�ַ�������ļ��У�����ֻ��Ҫ��������ļ����ܷ���ͼƬ�ˡ���Ȼ�����ͼƬ�ϴ󣬱�����������ܡ����url-loader�ṩ��һ��limit������С��limit�ֽڵ��ļ��ᱻתΪDataURl������limit�Ļ���ʹ��file-loader����copy��

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

test:/.(png|jpg|gif)/��ƥ��ͼƬ�ļ���׺���ơ�
use����ָ��ʹ�õ�loader��loader�����ò�����
limit���ǰ�С��500000B���ļ����Base64�ĸ�ʽ��д��JS��

Ϊʲôֻʹ����url-loader

�е�С���ᷢ�����ǲ�û����webpack.config.js��ʹ��file-loader��������Ȼ����ɹ��ˡ�������Ҫ�˽�file-loader��url-loader�Ĺ�ϵ��url-loader��file-loader��ʲô��ϵ�أ�����˵��url-loader��װ��file-loader��url-loader��������file-loader����ʹ��url-loaderʱ��ֻ��Ҫ��װurl-loader���ɣ�����Ҫ��װfile-loader����Ϊurl-loader������file-loader��ͨ������Ľ��ܣ����ǿ��Կ�����url-loader���������������

1.�ļ���СС��limit������url-loader������ļ�תΪDataURL��Base64��ʽ����

2.�ļ���С����limit��url-loader�����file-loader���д�������Ҳ��ֱ�Ӵ���file-loader��

Ҳ����˵����ʵ����ֻ��װһ��url-loader�Ϳ����ˡ�����Ϊ���Ժ�Ĳ������㣬���������˳�㰲װ��file-loader��

��09�ڣ�ͼƬ���ӣ�CSS������ͼƬ·������

CSS����:extract-text-webpack-plugin

��Щ�򵥵Ľ���ҳ���У����JavasScriptҳ������ǳ��٣����󲿷ִ��붼��CSS�У���ʱ����Ŀ�鳤��Ҫ���CSS������ȡ�����������Ժ���ġ�������������㲻Ҫ���ţ��Ѿ��д���Ϊ����׼�����˶���Ĳ����plugin����

extract-text-webpack-plugin

alt
�������Ϳ��������Ľ��������ȡCSS�����󣬵���webpack�ٷ���ʵ����������������������ΪCSS��Ӧ�ô����JavasScript�����Լ���http��������������ʵ�е�����������������ǰ���ܿ��Ƶģ���Щ���������ǲ��ܿ��Ƶģ�����CSS��������һ���Ⱥ����ɲ����������

��װ��¼�ƿγ�ʱ�İ汾��3.0.0�汾��ֱ��ʹ��npm install �Ϳ��԰�װ��

npm install --save-dev extract-text-webpack-plugin
���룺��װ��ɺ���Ҫ����require���롣

const extractTextPlugin = `require`("extract-text-webpack-plugin");
����Plugins������ɹ�����Ҫ��plugins�����н������á�����ֻҪnewһ���������Ϳ����ˡ�

new extractTextPlugin("/css/index.css")
�����/css/index.css�Ƿ�����·��λ�á��ⲿ������ɺ󣬰�װ���룺��Ҫ�޸�ԭ�����ǵ�style-loader��css-loader��

�޸Ĵ������¡�

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

�����ϱ����Ĳ��󣬾Ϳ���ʹ��webpack���д���ˡ�

ͼƬ·�����⣺

����extract-text-webpack-plugin��������ɵľͰ�CSS�ļ������˳���������CSS·��������ȷ���ܶ�С���������������컹��û��ͷ��������Ҳ�����˺ܶ�Ľ���������Ҿ�����õĽ��������ʹ��publicPath�������Ҳһֱ���á�

publicPath������webpack.config.js�ļ���outputѡ���У���Ҫ���þ��Ǵ���̬�ļ�·���ġ�

�ڴ���ǰ��������webpack.config.js �Ϸ�����һ�����󣬽�website��

var website ={
    publicPath:"http://192.168.1.108:1717/"
}
ע�⣬�����IP�Ͷ˿ڣ����㱾����ip��������devServer���õ�IP�Ͷ˿ڡ�

Ȼ����outputѡ����������������publicPath���ԡ�

//�����ļ���������
    output:{
        //�����·��������Node�﷨
        path:path.resolve(__dirname,'dist'),
        //������ļ�����
        filename:'[name].js',
        publicPath:website.publicPath
    },

������ɺ�����ʹ��webpack������д������ᷢ��ԭ�������·����Ϊ�˾���·�������������ٶȸ��졣

��10�ڣ�ͼƬ���ӣ�����HTML�е�ͼƬ

html-withimg-loader

html-withimg-loader�������ǽ�����ص��ˣ������������Ǻܻ�Ҳ���Ҹ���ϲ����һ��Сloader����������������hmtl�ļ��������ǩ�����⡣

��װ��

npm install html-withimg-loader --save
����loader

webpack.config.js

{
    test: /\.(htm|html)$/i,
     use:[ 'html-withimg-loader'] 
}

Ȼ�����ն��п��Խ��д���ˡ���ᷢ��images���ܺõĴ���ˡ�����·��Ҳ��ȫ��ȷ��

��11�ڣ�CSS���ף�Less�ļ��Ĵ���ͷ���

���Less�ļ�

��װ:

Ҫʹ��Less������Ҫ���Ȱ�װLess�ķ��񣬵�ȻҲ����npm�����а�װ��

npm install --save-dev less
����Ҫ��װLess-loader�������ʹ�á�

npm n install --save-dev less-loader
дloader���ã�

��װ�ú���Ҫ��webpack.config.js���дloader���ã���ȻҪ����ȷ������CSS��������Ҫstyle-loader��css-loader�İ���������������loaderǰ���Ѿ������ˣ�����������Ͳ��ظ��ˣ�����㻹��������loader����Ϥ�������л�ȥ��ǰ�ߵĵ���ڰɡ�

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
��дһ��less�ļ�

����webpack�����ú��ˣ����ǻ���Ҫ��дһ��less�ļ�����������Ϊblack.less.���ֻ��һ���Ǿ��ǰ�һ����ı������óɺ�ɫ��

black.less

@base :#000;
#gogo{
    width:300px;
    height:300px;
    background-color:@base;
}
less
����#gogo�ǲ��ID���ơ�@base���������õı������ơ�

���뵽����entery.js�ļ���

import less from './css/black.less';
�������ǾͿ��԰�less�ļ����д���ˡ����ǿ���ʹ��webpack��������һ�ԡ�

��Lees�ļ����롣

����֮ǰ����extract-text-webpack-plugin�����������Less�ļ���������ķ������������һ����֮ǰ�������ڵ�09���н������������Ǿ�ֻ��less��loader���÷��������˴������տ���Ƶ��

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

���úú���ᷢ��less�����뵽��index.css�ļ��

��12�ڣ�CSS���ף�SASS�ļ��Ĵ���ͷ���

��װSASS�����loader
����ĿĿ¼����npm��װ��������
node-sass��npm n install --save-dev node-sass
sass-loader��npm install --save-dev sass-loader

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
 
 ��13�ڣ�CSS���ף��Զ�����CSS3����ǰ׺
 ʲô������ǰ׺

����������һ�´��룺

-webkit-transform: rotate(45deg);
        transform: rotate(45deg);
Ϊ��������ļ����ԣ���ʱ�����Ǳ������-webkit,-ms,-o,-moz��Щǰ׺��Ŀ�ľ���������д��ҳ����ÿ��������ж�����˳�����С�

PostCSS

PostCSS��һ��CSS�Ĵ���ƽ̨�������԰������CSSʵ�ָ���Ĺ��ܣ����ǽ������Ǿ�ͨ�����е�һ����ǰ׺�Ĺ��ܣ������˽�һ��PostCSS��

��װ

��Ҫ��װ������postcss-loader ��autoprefixer���Զ����ǰ׺�Ĳ����

npm install --save-dev postcss-loader autoprefixer
postcss.config.js

postCSS�Ƽ�����Ŀ��Ŀ¼����webpack.config.jsͬ����������һ��postcss.config.js�ļ���

postcss.config.js

module.exports = {
    plugins: [
        require('autoprefixer')
    ]
}

����Ƕ�postCSSһ���򵥵����ã�������autoprefixer�������postCSSӵ�����ǰ׺��������������� can i use ��������Ӧ��css3����ǰ׺��

��дloader

��postcss.config.js������ɺ����ǻ���Ҫ��д���ǵ�loader���á�

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

��ȡCSS
������ȡCSS��loader����.

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


��14�ڣ�CSS���ף�����δʹ�õ�CSS
PurifyCSS
ʹ��PurifyCSS���Դ�����CSS���࣬�������Ǿ���ʹ�õ�BootStrap(140KB)�Ϳ��Լ��ٵ�ֻ��35KB��С������ʵ�ʿ��������Ƿǳ����õġ�

��װPurifyCSS-webpack
��������Ϳ��Կ�������һ�������������loader�����������Ҫ��װ����Ҫ���롣 PurifyCSS-webpackҪ������purify-css�������
��������������Ҫ��װ��

npmn  i -D purifycss-webpack purify-css
�����-D������ǨCsave-dev ,ֻ��һ����д��

����glob
��Ϊ������Ҫͬ�����htmlģ�壬����������Ҫ����node��glob����ʹ�á���webpack.config.js�ļ�ͷ������glob��
const glob = `require`('glob');
����purifycss-webpack
ͬ����webpack.config.js�ļ�ͷ������purifycss-webpack
const PurifyCSSPlugin = `require`("purifycss-webpack");
����plugins

������ɺ�������Ҫ��webpack.config.js������plugins���������£��ص㿴��Ʋ��֡�

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

����������һ��paths����Ҫ������htmlģ�壬purifycss����������û��������ļ���������Щcss��ʹ���ˡ�

ע�⣺ʹ���������������extract-text-webpack-plugin����������������ǰ�ߵĿγ��Ѿ�������ˡ�����㻹��������ѧһ�¡�

���ú��ϱߵĴ��룬���ǿ��Թ�����css�ļ���дһЩ�ò��������ԣ�Ȼ����webpack�������ᷢ��û�õ�CSS�Ѿ��Զ�����ɾ�����ˡ��ڹ����мǵ�һ��Ҫ�������plugins����Ϊ������������������ǳ����á�
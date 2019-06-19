自适应相关了解：

<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
对于IE9一下的浏览器
<!–[if lt IE 9]><script src=”http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js”></script><![endif]–>
选择加载CSS
“自适应网页设计”的核心，就是CSS3引入的Media Query模块。
它的意思就是，自动探测屏幕宽度，然后加载相应的CSS文件。
<link rel="stylesheet" type="text/css" media="screen and (max-device-width: 400px)" href="tinyScreen.css"/>
图片的自适应（fluid image）
除了布局和文本，”自适应网页设计”还必须实现图片的自动缩放。
这只要一行CSS代码：img { max-width: 100%;}
这行代码对于大多数嵌入网页的视频也有效，所以可以写成：img, object { max-width: 100%;}
老版本的IE不支持max-width，所以只好写成：img { width: 100%; }
此外，windows平台缩放图片时，可能出现图像失真现象。这时，可以尝试使用IE的专有命令：img { -ms-interpolation-mode: bicubic; }
或者，Ethan Marcotte的imgSizer.js。
addLoadEvent(function() { var imgs = document.getElementById("content").getElementsByTagName("img"); imgSizer.collate(imgs); });
不过，有条件的话，最好还是根据不同大小的屏幕，加载不同分辨率的图片。有很多方法可以做到这一条，服务器端和客户端都可以实现
如何区分普通显示屏和高清显示器
devicePixelRatio，使用它的值区分普通和高清晰度显示器，在细节中，你可以看到张欣旭写的一篇文章，“简单介绍了装置的像素比devicepixelratio，写作很好。
事实上，它是设备上物理像素和设备独立像素（DIP）的比例，这是devicepixelratio =屏幕的物理像素/设备独立像素 。

1、兼容移动设备
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

2、因为IE8既不支持HTML5也不支持CSS3 Media，所以我们需要加载两个JS文件，来保证我们的代码实现兼容效果
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

3、设置IE渲染方式默认为最高(这部分可以选择添加也可以不添加)
<meta http-equiv="X-UA-Compatible" content="IE=edge">  IE的文档模式永远都是最新的
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  固定的IE版本
<meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">Google_Chrome_Frame 谷歌内嵌浏览器框架GCF


三角形CSS：

<!-- 向上的三角形 -->
<div class="triangle_border_up">
    <span></span>
</div>
                                                      
<!-- 向下的三角形 -->
<div class="triangle_border_down">
    <span></span>
</div>
                                                      
<!-- 向左的三角形 -->
<div class="triangle_border_left">
    <span></span>
</div>
                                                      
<!-- 向右的三角形 -->
<div class="triangle_border_right">
    <span></span>
</div>
　　CSS:

/*向上*/
.triangle_border_up{
    width:0;
    height:0;
    border-width:0 30px 30px;
    border-style:solid;
    border-color:transparent transparent #333;/*透明 透明  灰*/
    margin:40px auto;
    position:relative;
}
.triangle_border_up span{
    display:block;
    width:0;
    height:0;
    border-width:0 28px 28px;
    border-style:solid;
    border-color:transparent transparent #fc0;/*透明 透明  黄*/
    position:absolute;
    top:0px;
    left:0px;
}
/*向下*/
.triangle_border_down{
    width:0;
    height:0;
    border-width:30px 30px 0;
    border-style:solid;
    border-color:#333 transparent transparent;/*灰 透明 透明 */
    margin:40px auto;
    position:relative;
}
.triangle_border_down span{
    display:block;
    width:0;
    height:0;
    border-width:28px 28px 0;
    border-style:solid;
    border-color:#fc0 transparent transparent;/*黄 透明 透明 */
    position:absolute;
    top:0px;
    left:0px;
}
/*向左*/
.triangle_border_left{
    width:0;
    height:0;
    border-width:30px 30px 30px 0;
    border-style:solid;
    border-color:transparent #333 transparent transparent;/*透明 灰 透明 透明 */
    margin:40px auto;
    position:relative;
}
.triangle_border_left span{
    display:block;
    width:0;
    height:0;
    border-width:28px 28px 28px 0;
    border-style:solid;
    border-color:transparent #fc0 transparent transparent;/*透明 黄 透明 透明 */
    position:absolute;
    top:0px;
    left:0px;
}
/*向右*/
.triangle_border_right{
    width:0;
    height:0;
    border-width:30px 0 30px 30px;
    border-style:solid;
    border-color:transparent transparent transparent #333;/*透明 透明 透明 灰*/
    margin:40px auto;
    position:relative;
}
.triangle_border_right span{
    display:block;
    width:0;
    height:0;
    border-width:28px 0 28px 28px;
    border-style:solid;
    border-color:transparent transparent transparent #fc0;/*透明 透明 透明 黄*/
    position:absolute;
    top:0px;
    left:0px;
}

perspective 设置3D变换距离的方法

padding应用：

一个元素的 padding，如果值是一个百分比，那这个百分比是相对于其父元素的宽度而言的，padding-bottom 也是如此。
使用 padding-bottom 来代替 height 来实现高度与宽度成比例的效果，将 padding-bottom设置为想要实现的 height 的值。同时将
其 height 设置为 0 以使元素的“高度”等于 padding-bottom 的值，从而实现需要的效果

blockquote 块引用 增加边距与缩进  , q短引用 会出现引号  
quotes：属性
1、none规定 "content" 属性的 "open-quote" 和 "close-quote" 的值不会产生任何引号。
2、string string string string	 定义要使用的引号。前两个值规定第一级引用嵌套，后两个值规定下一级引号嵌套。
3、inherit	规定应该从父元素继承 quotes 属性的值。

@page 设置页面打印相关的内容


SVG

path元素是SVG基本形状中最强大的一个，它不仅能创建其他基本形状，还能创建更多其他形状。
你可以用path元素绘制矩形（直角矩形或者圆角矩形）、圆形、椭圆、折线形、多边形，以及一些其他的形状，
例如贝塞尔曲线、2次曲线等曲线。
path元素的形状是通过属性d来定义的，属性d的值是一个“命令+参数”的序列。
下面的命令可用于路径数据：

M = moveto
L = lineto
H = horizontal lineto 水平
V = vertical lineto  竖直
C = curveto  曲线
S = smooth curveto  光滑的曲线
Q = quadratic Bézier curve
T = smooth quadratic Bézier curveto  
A = elliptical Arc  椭圆形的弧
Z = closepath  闭合路径

A ：
参数：(rx ry x-axis-rotation large-arc-flag sweep-flag x y)+rx 
ry 是椭圆的两个半轴的长度。
x-axis-rotation 是椭圆相对于坐标系的旋转角度，角度数而非弧度数。
large-arc-flag 是标记绘制大弧(1)还是小弧(0)部分。
sweep-flag 是标记向顺时针(1)还是逆时针(0)方向绘制。x y 是圆弧终点的坐标。
描述：从当前点绘制一段椭圆弧到点 (x, y)，椭圆的大小和方向由 (rx, ry) 和 x-axis-rotation 参数决定，
x-axis-rotation 参数表示椭圆整体相对于当前坐标系统的旋转角度。
椭圆的中心坐标?(cx, cy) 会自动进行计算从而满足其它参数约束。
large-arc-flag 和 sweep-flag 也被用于圆弧的计算与绘制。

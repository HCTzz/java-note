translate：移动   

-- transform的方法
transform: translate(50px, 100px);
-ms-transform: translate(50px,100px);
-webkit-transform: translate(50px,100px);
-o-transform: translate(50px,100px);
-moz-transform: translate(50px,100px);
 
transform：变形

 旋转：rotate() 顺时针旋转给定的角度，允许负值 rotate(30deg)
 扭曲：skew() 元素翻转给定的角度,根据给定的水平线（X 轴）和垂直线（Y 轴）
 参数：skew(30deg,20deg)
 缩放：scale() 放大或缩小，根据给定的宽度（X 轴）和高度（Y 轴）参数： scale(2,4)
 移动：translate() 平移，传进 x,y值，代表沿x轴和y轴平移的距离
 所有的2D转换方法组合在一起： matrix()  旋转、缩放、移动以及倾斜元素 matrix(scale.x ,, , scale.y , translate.x, translate.y)      
 
transition：允许CSS属性值在一定的时间区间内平滑的过渡，需要事件的触发，例如单击、获取焦点、失去焦点等。

transition:property duration timing-function delay;

 例如：width height 为none时停止所有的运动，可以为transform
 duration:持续时间
 timing-function:ease等
 delay:延迟
 注意：当property为all的时候所有动画
 例如：transition:width 2s ease 0s;
父子通信：
父 --> 子 props

子 --> 父 $on $emit 


//元数据不改变，只改变显示的效果
filters:{//过滤器
    toFixed(input,param1,aprams2){
        return input.foixed(param1)
    }
}
<!-- 过滤器 原数据不变的情况 只改变显示的效果  管道符 -->
{{num * price | toFixed(2,3)}} 


#解决在数据未加载完之前出现模板标签
v-cloak
#用法：这个指令保持在元素上直到关联实例结束编译。和 CSS 规则如[v-cloak] { display: none } 一起用时，这个指令可以隐藏未编译的 Mustache 标签直到实例准备完毕。示例：
[v-cloak] {
  display: none;
}
<div v-cloak>
  {{ message }}
</div>

#计算属性会缓存
#当属性写成函数形式时，默认调用的是get 方法，不能只写set方法
sum:{
    get(){
        return xxx;
    }
}
等价于
sum(){
    return xxx;
}

#watch computed
watch:用于异步（不需要返回值）
computed：不能用于异步（在返回值时出现问题，会将return返回在异步的回调里）

#指令
<button v-color="true"></button>
directives:{
    color(el,bindings){ //el指代的是当前按钮
       // console.log(arguments);
       bindings.value //是当前指令的值
       el.style.background = bindings.value;
    }
}

#钩子函数
//生命周期
beforeCreate  //一般用不到
created //创建  ajax获取数据 
beforeMount //没有实际意义   需要有渲染的模版，el选项
mounted  //挂载  真实DOM渲染完了，可以操作DOM
beforeUpdate //  更新之前  一般会用watch替换
updated //更新
beforeDestory destoryed //销毁

#拖拽
<div v-drag></div>
directives:{
    drag(el):{
        el.onmousedown = function(e){
            var disx = e.pageX - el.offsetLeft;
            var disy = e.pageY - el.offsetTop;
            document.onmousemove = function(e){
                el.style.left = e.pageX - disx + "px";
                el.style.top = e.pageY - disy + "px";
            }
            document.onmouseup = function(){
                document.onmousemove = document.onmouseup = null
            }
            e.preeventDefault();
        }
    }
}

#vue深度监听数组的变化
watch:{
    'temperature':{//要监听的数值
        handler:function(newValue,oldValue){//新值，旧值。
            console.log(newValue)
        },
        deep:true,
    }
}
#由于 JavaScript 的限制，Vue 不能检测以下变动的数组：
#当你利用索引直接设置一个项时，例如：vm.items[indexOfItem] = newValue
#当你修改数组的长度时，例如：vm.items.length = newLength
#使用vue.$set
// app-10
var app10 = new Vue({
    el:"#app-10",
    data:{
        msg:{
            age:18
        }
    }
})
Vue.set(app10.msg,"name","zhangsan");

#实例方法
this.$data vm上的数据
this.$watch 监控
this.$el 当前el元素
this.$set //后加的属性实现响应式变化
this.options //所有选项
this.$refs 引用的 如下。 如果DOM元素不是通过V-FOR循环渲染的则只能取得一个，反之则取得多个。dom渲染是异步的。如果想在挂载后取得更新后的DOM则需要加在 This.nextTick异步方法里
this.nextTick //异步方法  
<div id="app">{{msg}}</div>
<p ref="myo">hello</p>
let vm = new Vue({
    el:'app',
    data:{
        msg:'hello'
    },
    mounted(){
        this.$refs。myo ==> document.getElementByTag('p')[0]
        this.$nextTick //异步方法当代码执行完后执行  
        this.$options //所有选项
    }
})

#组件
1、页面级组件
2、将可复用的部分抽离出来（基础组件）
#用法：全局组件 生命一次到处使用
#局部组件：必须告诉这个组件属于谁:
1、创建这个组件 2、注册组件 3、引用组件
let my-component = {
    template:`<div>我很英俊</div>`,
}
new vue({
    el:'#app',
    components:{
        component
    }
})

#组件中的数据必须是函数类型：
Vue.component('my-tag',{
    template:'',
    data(){

    }
})

#父子组件通信
prop
第一个的属性所有值都是字符串
<son m="" :m=""><son>
props:{
    m:{
        type:[String,Function.....],
        required:true ,
        default:0 # 不能与required同用
        validator(val){
            return val > 300 #自定义验证器
        }
    }
}

#slot 插槽


#路由
<router-link :to="{path:'/article',params:{c:1,a:2}}"/>
let routes = {
    {path:'/article/:c/:a',component:article,name:'pro'}
}
$route.params.xxx
用watch箭筒路由参数的变化
watch:{
    $route(){

    }
}

#transtion-keep  切换动画 进入退出都有对应的样式 切换时会销毁
<transtion enter-active-class="animated fadeIn"
           leave-active-class="animated fadeOut" 需要引用animated css
            mode="in-out"> #模式，先进后出  out-in 先出后进
    <keep-alive>
    <router-view style="position：absolute;top:0px;left:0px;width:100%"></router-view>
    </keep-alive>
</transtion>

#安装的文件
#vue-cli  vue  脚手架
vue init webpack <项目名>
cd 项目名字
npm install
#

es6数组方法：
#只返回boolean
includes  
#返回找到的值 找不到返回的undefined
find(callback(item,index))  
解构

#找到true后停止，返回true
some 
#找到false后停止，返回false
every

#  收敛函数  四个参数 返回的是叠加后的结果
#prev 代表的是数组的第一项，next是数组的第二项
#第二次prev是undefined【需要写返回值】，next是数组的第三项
reduce
[1,2,3,4,5,6].reduce(function(prev,next,index,item){
    return prev + next；
})
[{price:20,count:2},{price:30,count:1},{price:50,count:6}].reduce(
    function(prev,next){
        return prev + next.price*next.count
    },0//默认指定第一次的prev
)
filter  map  every  some  forEach  indexOf 

#数组扁平化
[[1,2,3,4],[5,6,7,8],[9,10,11]].reduce(function(prev,next){
    return prev.concat(next);
})



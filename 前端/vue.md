����ͨ�ţ�
�� --> �� props

�� --> �� $on $emit 


//Ԫ���ݲ��ı䣬ֻ�ı���ʾ��Ч��
filters:{//������
    toFixed(input,param1,aprams2){
        return input.foixed(param1)
    }
}
<!-- ������ ԭ���ݲ������� ֻ�ı���ʾ��Ч��  �ܵ��� -->
{{num * price | toFixed(2,3)}} 


#���������δ������֮ǰ����ģ���ǩ
v-cloak
#�÷������ָ�����Ԫ����ֱ������ʵ���������롣�� CSS ������[v-cloak] { display: none } һ����ʱ�����ָ���������δ����� Mustache ��ǩֱ��ʵ��׼����ϡ�ʾ����
[v-cloak] {
  display: none;
}
<div v-cloak>
  {{ message }}
</div>

#�������ԻỺ��
#������д�ɺ�����ʽʱ��Ĭ�ϵ��õ���get ����������ֻдset����
sum:{
    get(){
        return xxx;
    }
}
�ȼ���
sum(){
    return xxx;
}

#watch computed
watch:�����첽������Ҫ����ֵ��
computed�����������첽���ڷ���ֵʱ�������⣬�Ὣreturn�������첽�Ļص��

#ָ��
<button v-color="true"></button>
directives:{
    color(el,bindings){ //elָ�����ǵ�ǰ��ť
       // console.log(arguments);
       bindings.value //�ǵ�ǰָ���ֵ
       el.style.background = bindings.value;
    }
}

#���Ӻ���
//��������
beforeCreate  //һ���ò���
created //����  ajax��ȡ���� 
beforeMount //û��ʵ������   ��Ҫ����Ⱦ��ģ�棬elѡ��
mounted  //����  ��ʵDOM��Ⱦ���ˣ����Բ���DOM
beforeUpdate //  ����֮ǰ  һ�����watch�滻
updated //����
beforeDestory destoryed //����

#��ק
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

#vue��ȼ�������ı仯
watch:{
    'temperature':{//Ҫ��������ֵ
        handler:function(newValue,oldValue){//��ֵ����ֵ��
            console.log(newValue)
        },
        deep:true,
    }
}
#���� JavaScript �����ƣ�Vue ���ܼ�����±䶯�����飺
#������������ֱ������һ����ʱ�����磺vm.items[indexOfItem] = newValue
#�����޸�����ĳ���ʱ�����磺vm.items.length = newLength
#ʹ��vue.$set
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

#ʵ������
this.$data vm�ϵ�����
this.$watch ���
this.$el ��ǰelԪ��
this.$set //��ӵ�����ʵ����Ӧʽ�仯
this.options //����ѡ��
this.$refs ���õ� ���¡� ���DOMԪ�ز���ͨ��V-FORѭ����Ⱦ����ֻ��ȡ��һ������֮��ȡ�ö����dom��Ⱦ���첽�ġ�������ڹ��غ�ȡ�ø��º��DOM����Ҫ���� This.nextTick�첽������
this.nextTick //�첽����  
<div id="app">{{msg}}</div>
<p ref="myo">hello</p>
let vm = new Vue({
    el:'app',
    data:{
        msg:'hello'
    },
    mounted(){
        this.$refs��myo ==> document.getElementByTag('p')[0]
        this.$nextTick //�첽����������ִ�����ִ��  
        this.$options //����ѡ��
    }
})

#���
1��ҳ�漶���
2�����ɸ��õĲ��ֳ�����������������
#�÷���ȫ����� ����һ�ε���ʹ��
#�ֲ�����������������������˭:
1������������ 2��ע����� 3���������
let my-component = {
    template:`<div>�Һ�Ӣ��</div>`,
}
new vue({
    el:'#app',
    components:{
        component
    }
})

#����е����ݱ����Ǻ������ͣ�
Vue.component('my-tag',{
    template:'',
    data(){

    }
})

#�������ͨ��
prop
��һ������������ֵ�����ַ���
<son m="" :m=""><son>
props:{
    m:{
        type:[String,Function.....],
        required:true ,
        default:0 # ������requiredͬ��
        validator(val){
            return val > 300 #�Զ�����֤��
        }
    }
}

#slot ���


#·��
<router-link :to="{path:'/article',params:{c:1,a:2}}"/>
let routes = {
    {path:'/article/:c/:a',component:article,name:'pro'}
}
$route.params.xxx
��watch��Ͳ·�ɲ����ı仯
watch:{
    $route(){

    }
}

#transtion-keep  �л����� �����˳����ж�Ӧ����ʽ �л�ʱ������
<transtion enter-active-class="animated fadeIn"
           leave-active-class="animated fadeOut" ��Ҫ����animated css
            mode="in-out"> #ģʽ���Ƚ����  out-in �ȳ����
    <keep-alive>
    <router-view style="position��absolute;top:0px;left:0px;width:100%"></router-view>
    </keep-alive>
</transtion>

#��װ���ļ�
#vue-cli  vue  ���ּ�
vue init webpack <��Ŀ��>
cd ��Ŀ����
npm install
#

es6���鷽����
#ֻ����boolean
includes  
#�����ҵ���ֵ �Ҳ������ص�undefined
find(callback(item,index))  
�⹹

#�ҵ�true��ֹͣ������true
some 
#�ҵ�false��ֹͣ������false
every

#  ��������  �ĸ����� ���ص��ǵ��Ӻ�Ľ��
#prev �����������ĵ�һ�next������ĵڶ���
#�ڶ���prev��undefined����Ҫд����ֵ����next������ĵ�����
reduce
[1,2,3,4,5,6].reduce(function(prev,next,index,item){
    return prev + next��
})
[{price:20,count:2},{price:30,count:1},{price:50,count:6}].reduce(
    function(prev,next){
        return prev + next.price*next.count
    },0//Ĭ��ָ����һ�ε�prev
)
filter  map  every  some  forEach  indexOf 

#�����ƽ��
[[1,2,3,4],[5,6,7,8],[9,10,11]].reduce(function(prev,next){
    return prev.concat(next);
})



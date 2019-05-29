ES6数组操作
json = {
	'0':'test',
	'1':'test1',
	'2':'test2',
	length:3
}
Array.from(json) 解析成数组
let arr =Array.of('3,4,5,6');
Array.of() 转换成数组

find() 实例方法
let arr = [1,2,3,4,5,6,7,8,9]
arr.find(function(value,index,arr){
	return value > 5;
});
fill() 替换
let arr = ['demo','demo1','demo2'];
arr.fill('web',1,3) //['demo','web','web']

for(of) 遍历
for(let item of arr.entries());
for(let [index,val] of arr.entries())
let list = arr.entries()
list.next().value


对象的函数解构
let json = {
	a:'ee',
	b:'hh'
}
function fun({a,b='jj'}){
	console.log(a,b);
}
fun(json)

对象赋值
let a = 'a'
let b = 'b'
let obj = {a,b}
console.log(obj);

let key = 'skill'
let obj = {
	[key] : 'web'
}

let obj1 = {name : '123'}
let obj2 = {name : '234'}
console.log(obj1.name === obj2.name)
console.log(Object.is(obj1.name,obj2.name))
区别： === 同值相等   is严格相等
console.log(+0 === -0) //true
console.log(NaN === NaN); //false
console.log(Object.is(+0,-0)) //false
console.log(Object.is(NaN,NaN)) //true


let a = {a:'a'}
let b = {b:'b'}
let c = {c:'c'}
let d = Object.assign(a,b,c);

Symbol 全局标记  原始数据
let f = Symbol();
console.log(typeof(f));

let obj = {name:'js',skill:'web'}
let age = Symbol();
obj[age] = 18
for(let item in obj){
	console.log(obj[item]);
}

set  weakSet

let setArr = new Set(['js','js1']);
setArr.add('js2');
setArr.has('js2');//true
setArr.delete('js2');
for(let item of setArr){
	console.log(item)
}
console.log(setArr.size)
console.log(setArr);

let weak = new WeakSet();
let obj = {a:'js',b:'dw'};
weak.add(obj);

map数据结构
var map = new Map();
let json = {
	name:'12',
	age:18
}
map.set(json,'iam');
map.get(json);
map.delete(json);
map.size
map.has(json);//true


proxy 代理
let obj = {
	add:function(val){
		return val + 100
	},
	name : 'I am Jspang'
}

let pro = new Proxy({
	add:function(val){ //对象体
		return val + 100
	},
	name : 'I am Jspang'
	},
	{
		get:function(target,key,property){
			console.log('get');
			return target[key]
		},
		set:function(target,key,val,recever){
			return target[key] = val;
		}
	});
	
	console.log(pro.name);
	
	let target = function(){
		return 'Iam'
	}
	
	let handler = {
		apply(target,ctx,args){
			console.log('do apply');
			return Reflect.apply(..arguments);
		}
	}
	let pro = new Proxy(target,handler);
	pro();
	
	
promise   //回调地狱
let state = 1;
function step1(resolve,reject){
	console.log('1、开始做饭');
	if(state == 1){
		resolve('洗菜做饭完成');
	}else{
		reject('菜饭出错');
	}
}

function step2(resolve,reject){
	console.log('2、开始吃饭');
	if(state == 1){
		resolve('吃饭完成');
	}else{
		reject('吃饭出错');
	}
}

function step3(resolve,reject){
	console.log('3、开始洗完');
	if(state == 1){
		resolve('洗完完成');
	}else{
		reject('洗完出错');
	}
}


new Promise(step1).then(function(val){
	console.log(val);
	return new Promise(step2);
}).then(function(val){
	console.log(val);
	return new Promise(step3);
}).then(function(val){

})

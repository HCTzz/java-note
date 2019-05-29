ES6�������
json = {
	'0':'test',
	'1':'test1',
	'2':'test2',
	length:3
}
Array.from(json) ����������
let arr =Array.of('3,4,5,6');
Array.of() ת��������

find() ʵ������
let arr = [1,2,3,4,5,6,7,8,9]
arr.find(function(value,index,arr){
	return value > 5;
});
fill() �滻
let arr = ['demo','demo1','demo2'];
arr.fill('web',1,3) //['demo','web','web']

for(of) ����
for(let item of arr.entries());
for(let [index,val] of arr.entries())
let list = arr.entries()
list.next().value


����ĺ����⹹
let json = {
	a:'ee',
	b:'hh'
}
function fun({a,b='jj'}){
	console.log(a,b);
}
fun(json)

����ֵ
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
���� === ֵͬ���   is�ϸ����
console.log(+0 === -0) //true
console.log(NaN === NaN); //false
console.log(Object.is(+0,-0)) //false
console.log(Object.is(NaN,NaN)) //true


let a = {a:'a'}
let b = {b:'b'}
let c = {c:'c'}
let d = Object.assign(a,b,c);

Symbol ȫ�ֱ��  ԭʼ����
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

map���ݽṹ
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


proxy ����
let obj = {
	add:function(val){
		return val + 100
	},
	name : 'I am Jspang'
}

let pro = new Proxy({
	add:function(val){ //������
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
	
	
promise   //�ص�����
let state = 1;
function step1(resolve,reject){
	console.log('1����ʼ����');
	if(state == 1){
		resolve('ϴ���������');
	}else{
		reject('�˷�����');
	}
}

function step2(resolve,reject){
	console.log('2����ʼ�Է�');
	if(state == 1){
		resolve('�Է����');
	}else{
		reject('�Է�����');
	}
}

function step3(resolve,reject){
	console.log('3����ʼϴ��');
	if(state == 1){
		resolve('ϴ�����');
	}else{
		reject('ϴ�����');
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

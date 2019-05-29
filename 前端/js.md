1、prototype 是函数(function) 的一个属性, 它指向函数的原型.
2、__proto__ 是对象的内部属性, 它指向构造器的原型, 对象依赖它进行原型链查询.

比较日期
return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));

判断是否在视口
var hiddenProperty = 'hidden' in document ? 'hidden' :    
    'webkitHidden' in document ? 'webkitHidden' :    
    'mozHidden' in document ? 'mozHidden' :    
    null;
var visibilityChangeEvent = hiddenProperty.replace(/hidden/i, 'visibilitychange');
var onVisibilityChange = function(){
    if (!document[hiddenProperty]) {    
        console.log('页面非激活');
    }else{
        console.log('页面激活')
    }
}
document.addEventListener(visibilityChangeEvent, onVisibilityChange);

contenteditable  区域可编辑属性


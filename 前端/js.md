1��prototype �Ǻ���(function) ��һ������, ��ָ������ԭ��.
2��__proto__ �Ƕ�����ڲ�����, ��ָ��������ԭ��, ��������������ԭ������ѯ.

�Ƚ�����
return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));

�ж��Ƿ����ӿ�
var hiddenProperty = 'hidden' in document ? 'hidden' :    
    'webkitHidden' in document ? 'webkitHidden' :    
    'mozHidden' in document ? 'mozHidden' :    
    null;
var visibilityChangeEvent = hiddenProperty.replace(/hidden/i, 'visibilitychange');
var onVisibilityChange = function(){
    if (!document[hiddenProperty]) {    
        console.log('ҳ��Ǽ���');
    }else{
        console.log('ҳ�漤��')
    }
}
document.addEventListener(visibilityChangeEvent, onVisibilityChange);

contenteditable  ����ɱ༭����


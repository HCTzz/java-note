translate���ƶ�   

-- transform�ķ���
transform: translate(50px, 100px);
-ms-transform: translate(50px,100px);
-webkit-transform: translate(50px,100px);
-o-transform: translate(50px,100px);
-moz-transform: translate(50px,100px);
 
transform������

 ��ת��rotate() ˳ʱ����ת�����ĽǶȣ�����ֵ rotate(30deg)
 Ť����skew() Ԫ�ط�ת�����ĽǶ�,���ݸ�����ˮƽ�ߣ�X �ᣩ�ʹ�ֱ�ߣ�Y �ᣩ
 ������skew(30deg,20deg)
 ���ţ�scale() �Ŵ����С�����ݸ����Ŀ�ȣ�X �ᣩ�͸߶ȣ�Y �ᣩ������ scale(2,4)
 �ƶ���translate() ƽ�ƣ����� x,yֵ��������x���y��ƽ�Ƶľ���
 ���е�2Dת�����������һ�� matrix()  ��ת�����š��ƶ��Լ���бԪ�� matrix(scale.x ,, , scale.y , translate.x, translate.y)      
 
transition������CSS����ֵ��һ����ʱ��������ƽ���Ĺ��ɣ���Ҫ�¼��Ĵ��������絥������ȡ���㡢ʧȥ����ȡ�

transition:property duration timing-function delay;

 ���磺width height Ϊnoneʱֹͣ���е��˶�������Ϊtransform
 duration:����ʱ��
 timing-function:ease��
 delay:�ӳ�
 ע�⣺��propertyΪall��ʱ�����ж���
 ���磺transition:width 2s ease 0s;
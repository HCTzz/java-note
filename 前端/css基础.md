����Ӧ����˽⣺

<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
����IE9һ�µ������
<!�C[if lt IE 9]><script src=��http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js��></script><![endif]�C>
ѡ�����CSS
������Ӧ��ҳ��ơ��ĺ��ģ�����CSS3�����Media Queryģ�顣
������˼���ǣ��Զ�̽����Ļ��ȣ�Ȼ�������Ӧ��CSS�ļ���
<link rel="stylesheet" type="text/css" media="screen and (max-device-width: 400px)" href="tinyScreen.css"/>
ͼƬ������Ӧ��fluid image��
���˲��ֺ��ı���������Ӧ��ҳ��ơ�������ʵ��ͼƬ���Զ����š�
��ֻҪһ��CSS���룺img { max-width: 100%;}
���д�����ڴ����Ƕ����ҳ����ƵҲ��Ч�����Կ���д�ɣ�img, object { max-width: 100%;}
�ϰ汾��IE��֧��max-width������ֻ��д�ɣ�img { width: 100%; }
���⣬windowsƽ̨����ͼƬʱ�����ܳ���ͼ��ʧ��������ʱ�����Գ���ʹ��IE��ר�����img { -ms-interpolation-mode: bicubic; }
���ߣ�Ethan Marcotte��imgSizer.js��
addLoadEvent(function() { var imgs = document.getElementById("content").getElementsByTagName("img"); imgSizer.collate(imgs); });
�������������Ļ�����û��Ǹ��ݲ�ͬ��С����Ļ�����ز�ͬ�ֱ��ʵ�ͼƬ���кܶ෽������������һ�����������˺Ϳͻ��˶�����ʵ��
���������ͨ��ʾ���͸�����ʾ��
devicePixelRatio��ʹ������ֵ������ͨ�͸���������ʾ������ϸ���У�����Կ���������д��һƪ���£����򵥽�����װ�õ����ر�devicepixelratio��д���ܺá�
��ʵ�ϣ������豸���������غ��豸�������أ�DIP���ı���������devicepixelratio =��Ļ����������/�豸�������� ��

1�������ƶ��豸
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

2����ΪIE8�Ȳ�֧��HTML5Ҳ��֧��CSS3 Media������������Ҫ��������JS�ļ�������֤���ǵĴ���ʵ�ּ���Ч��
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

3������IE��Ⱦ��ʽĬ��Ϊ���(�ⲿ�ֿ���ѡ�����Ҳ���Բ����)
<meta http-equiv="X-UA-Compatible" content="IE=edge">  IE���ĵ�ģʽ��Զ�������µ�
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  �̶���IE�汾
<meta http-equiv="X-UA-Compatible" content="IE=Edge��chrome=1">Google_Chrome_Frame �ȸ���Ƕ��������GCF


������CSS��

<!-- ���ϵ������� -->
<div class="triangle_border_up">
    <span></span>
</div>
                                                      
<!-- ���µ������� -->
<div class="triangle_border_down">
    <span></span>
</div>
                                                      
<!-- ����������� -->
<div class="triangle_border_left">
    <span></span>
</div>
                                                      
<!-- ���ҵ������� -->
<div class="triangle_border_right">
    <span></span>
</div>
����CSS:

/*����*/
.triangle_border_up{
    width:0;
    height:0;
    border-width:0 30px 30px;
    border-style:solid;
    border-color:transparent transparent #333;/*͸�� ͸��  ��*/
    margin:40px auto;
    position:relative;
}
.triangle_border_up span{
    display:block;
    width:0;
    height:0;
    border-width:0 28px 28px;
    border-style:solid;
    border-color:transparent transparent #fc0;/*͸�� ͸��  ��*/
    position:absolute;
    top:0px;
    left:0px;
}
/*����*/
.triangle_border_down{
    width:0;
    height:0;
    border-width:30px 30px 0;
    border-style:solid;
    border-color:#333 transparent transparent;/*�� ͸�� ͸�� */
    margin:40px auto;
    position:relative;
}
.triangle_border_down span{
    display:block;
    width:0;
    height:0;
    border-width:28px 28px 0;
    border-style:solid;
    border-color:#fc0 transparent transparent;/*�� ͸�� ͸�� */
    position:absolute;
    top:0px;
    left:0px;
}
/*����*/
.triangle_border_left{
    width:0;
    height:0;
    border-width:30px 30px 30px 0;
    border-style:solid;
    border-color:transparent #333 transparent transparent;/*͸�� �� ͸�� ͸�� */
    margin:40px auto;
    position:relative;
}
.triangle_border_left span{
    display:block;
    width:0;
    height:0;
    border-width:28px 28px 28px 0;
    border-style:solid;
    border-color:transparent #fc0 transparent transparent;/*͸�� �� ͸�� ͸�� */
    position:absolute;
    top:0px;
    left:0px;
}
/*����*/
.triangle_border_right{
    width:0;
    height:0;
    border-width:30px 0 30px 30px;
    border-style:solid;
    border-color:transparent transparent transparent #333;/*͸�� ͸�� ͸�� ��*/
    margin:40px auto;
    position:relative;
}
.triangle_border_right span{
    display:block;
    width:0;
    height:0;
    border-width:28px 0 28px 28px;
    border-style:solid;
    border-color:transparent transparent transparent #fc0;/*͸�� ͸�� ͸�� ��*/
    position:absolute;
    top:0px;
    left:0px;
}

perspective ����3D�任����ķ���

paddingӦ�ã�

һ��Ԫ�ص� padding�����ֵ��һ���ٷֱȣ�������ٷֱ���������丸Ԫ�صĿ�ȶ��Եģ�padding-bottom Ҳ����ˡ�
ʹ�� padding-bottom ������ height ��ʵ�ָ߶����ȳɱ�����Ч������ padding-bottom����Ϊ��Ҫʵ�ֵ� height ��ֵ��ͬʱ��
�� height ����Ϊ 0 ��ʹԪ�صġ��߶ȡ����� padding-bottom ��ֵ���Ӷ�ʵ����Ҫ��Ч��

blockquote ������ ���ӱ߾�������  , q������ ���������  
quotes������
1��none�涨 "content" ���Ե� "open-quote" �� "close-quote" ��ֵ��������κ����š�
2��string string string string	 ����Ҫʹ�õ����š�ǰ����ֵ�涨��һ������Ƕ�ף�������ֵ�涨��һ������Ƕ�ס�
3��inherit	�涨Ӧ�ôӸ�Ԫ�ؼ̳� quotes ���Ե�ֵ��

@page ����ҳ���ӡ��ص�����
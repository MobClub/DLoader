# DLoader #

DLoader����������ʱ����Mob��ͬ��ƷSDK����Ƚϳ��漯��Mob����SDK�ķ�ʽ��DLoader�����������С����ȫ��Դ����̬���������ҿɿ��Ը��ߡ�

��ǰDLoader�Ѿ�ʵ����[ShareSDK](http://sharesdk.mob.com/#/sharesdk)��[SMSSDK](http://sms.mob.com/#/sms)������Ʒ��������������Ӧ����Mob���κβ�Ʒ�ϣ�����Mob����Ĳ�Ʒ�ϡ�

���Ľ���DLoader��ʹ�÷����������£�

## Ŀ¼�ֲ� ##

clone�������DLoader��Ŀ����3��Ŀ¼��DLib��Dloader��Demo��DLib������ShareSDK��SMSSDK������Ŀ���°汾�ĺ���APK���ڷ�������Ŀʱ��ShareSDK���µ�2.6.6��SMSSDK���µ�2.0.0��DLoader����Ŀ���Ĵ��룻Demo������DLoader����ʾ���򣬶�ShareSDK��SMSSDK������Ŀ����Ҫ���ܽ��е��á�

���е���Ŀʹ��Eclipse�ķ�ʽ��֯���롣

## ���ɷ��� ##

������Ҫ��DLoader����Eclipse��

��Σ������ѡ��DLoader����jar�������߸�Demoһ����ֱ������DLoader��Ŀ���ұȽϽ���ǰ�ߡ�

�����[ShareSDK](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)��[SMSSDK](http://wiki.mob.com/android-%E7%9F%AD%E4%BF%A1sdk%E9%9B%86%E6%88%90%E6%96%87%E6%A1%A3/)�ٷ��ĵ�������һ�������ȵ���ShareSDK.init������ʼ��ShareSDK��֮�����Platform��showUser����share�����ҵ�񣻻����ȵ���SMSSDK.init������ʼ��SMSSDK��Ȼ��newһ��RegisterPage����show����ʵ�ֶ�����֤��У�顣

DLib�е�MobLibs.apkĬ�ϼ�����OnekeyShare��SMSSDK GUI������ԴGUI��Ŀ��

## �޸Ĵ��� ##

**DLoader�ǿ�Դ�ģ������������Ŀ���޸ĺ�ʹ�����еĴ���**���������Ŀ�У�һ������漰�����޸İ�����apk�ļ���������ƽ̨�ͱ��ؿ�������apk�ļ�������·��������apk�ļ��Ĳ��Ժ�����SDK��wrapper���롣

### apk�ļ���������ƽ̨�ͱ��ؿ����� ###

��ΪDLoaderʹ�õ�����SDK������ֱ������mob������������ӻ��߼���ShareSDK���ɵ�ƽ̨������������ӻ����SMSSDK���ؿ������������Բο�[ShareSDK](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)��[SMSSDK](http://wiki.mob.com/android-%E7%9F%AD%E4%BF%A1sdk%E9%9B%86%E6%88%90%E6%96%87%E6%A1%A3/)�Ĺٷ��ĵ�������ʵ�����������ӻ���ɾ�������������ļ����ѡ�

**ֻ��֧��ShareSDK 2.6.5��SMSSDK2.0.0�Ժ�İ汾**��

### �޸�apk�ļ�������·�� ###

DLoaderд����apk�ļ�������·��������ľ���λ���ڡ�m.dloader.DLoader����ġ�APK_URL���ֶ�

``` java
public class DLoader {
	private static final String APK_URL = "https://github.com/MobClub/DLoader/blob/master/DLib/MobLibs.apk?raw=true";

	������������
```

### �޸ĸ���apk�ļ��Ĳ��� ###

Ĭ�������DLoaderÿ��load������������apk��������ʾ���Ǳ�Ҫ�ġ��Ż���˼·���������жϱ���ʱ����apk�����û�У�������apk������У�����㱾��apk��md5��Ȼ����������������ص�ַ�������ַ��Ϊ�գ����������ļ��滻��������Ҫ���¡�

### �޸�����SDK��wrapper���� ###

DLoader������SDK�ĺ��Ĵ����ڡ�m.dloader.impl�����£���ֻ��ʵ��������һ���ֳ��õĹ��ܣ��������Ҫ����Ĺ��ܣ�����Ҫģ���Ѿ����ڵĴ��룬������ӡ�DLoader��wrapperԭ��ܼ򵥣�����**java�ķ���**�����÷��������Ͽ���ʵ��ԭ��SDK�Ķ��й����������ع��ܡ�

## ����˵�� ##

*DLoader������SDK��DLoaderֻ��SDK�İ��˹���*


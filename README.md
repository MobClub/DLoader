# ShareSDK_DLoader #

ShareSDK�ٷ������ṩ���Ǿ�̬jar����һ������SDK���߷������bug���ͻ��˳���Ͳ��ò����·����汾������ܵ��ºܴ����ʧ��DLoader������һ���̶��Ͻ����������⡣����ShareSDK�ľ�̬jar��������apk�����ڻ��������ͻ��˳����ټ���SDK�ľ�̬jar������ֻ��Я�����ڼ���apk�ļ򵥴��롣�ⲻ������ؼ��������ճ�������������һ��SDK�������⣬����ͨ�����벢�ύ�޸����apk��ʹ���ٵõ��޸������Ľ���DLoader��ʹ�÷����������£�

## Ŀ¼�ֲ� ##

clone�������DLoader��Ŀ����3��Ŀ¼��DLibs��Dloader��Demos��DLibs������ShareSDK������SDK���ڷ�������Ŀʱ��ShareSDK���µ�2.6.5�汾��DLoader����Ŀ���Ĵ��룻Demos������ShareSDKLoader��һ����ʾ����ShareSDK�ĳ��򡪡�Dloader�����Ͽ���ʹ����mob�����в�Ʒ���ϣ���Ŀǰֻ��ShareSDK��

���е���Ŀʹ��Eclipse�ķ�ʽ��֯���룬������ʱû��Ǩ�Ƶ�AS�ļƻ���

## ���ɷ��� ##

������Ҫ��DLoader����Eclipse��

��Σ������ѡ��DLoader����jar�������߸�Demoһ����ֱ������DLoader��Ŀ���ұȽϽ���ǰ�ߡ�

�����[ShareSDK�Ĺٷ��ĵ�](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)������һ�����ȵ���ShareSDK.init������ʼ��ShareSDK��֮�����Platform��showUser����share�����ҵ��

**DLoader��ʹ��OnekeyShare GUI��**������ֻ��ͨ���޽���ӿ�ʵ��ҵ���߼���

## �޸Ĵ��� ##

**DLoader�ǿ�Դ�ģ������������Ŀ���޸ĺ�ʹ�����еĴ���**���������Ŀ�У�һ������漰�����޸İ�����apk�ļ���������ƽ̨������apk�ļ�������·��������apk�ļ��Ĳ��Ժ�ShareSDK���wrapper���롣

### �޸�apk�ļ���������ƽ̨���� ###

��ΪDLoader�ṩ��ShareSDK��ֱ������mob������������ӻ��߼��ټ��ɵ�ƽ̨����������Բο�[ShareSDK�Ĺٷ��ĵ�](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)������ʵ�����������ӻ���ɾ������ƽ̨��jar�����ѡ�

**ֻ��֧��ShareSDK 2.6.5�Ժ�İ汾**��

### �޸�apk�ļ�������·�� ###

DLoaderд����apk�ļ�������·��������ľ���λ���ڡ�m.dloader.sharesdk.ShareSDK����ġ�downloadSDK������

``` java
private static void downloadSDK(File SDKFile) {
	try {
		String urlStr = "https://github.com/ShareSDKPlatform/ShareSDK_DLoader/blob/master/DLibs/ShareSDK/ShareSDK.apk?raw=true";
		URL ourl = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) ourl.openConnection();
		conn.connect();
	������������
```

### �޸ĸ���apk�ļ��Ĳ��� ###

Ĭ�������DLoaderÿ��initSDK������������apk��������ʾ���Ǳ�Ҫ�ġ��Ż���˼·���������жϱ���ʱ����apk�����û�У�������apk������У�����㱾��apk��md5��Ȼ����������������ص�ַ�������ַ��Ϊ�գ����������ļ��滻��������Ҫ���¡�

### �޸�ShareSDK���wrapper���� ###

DLoader��ShareSDK�ĺ��Ĵ����ڡ�m.dloader.sharesdk.ShareSDK.java���У���ֻ��ʵ����һ����ShareSDK���õĹ��ܣ��������Ҫ����Ĺ��ܣ�����Ҫģ���Ѿ����ڵĴ��룬������ӡ�DLoader��wrapperԭ��ܼ򵥣�����**java�ķ���**�����÷��������Ͽ���ʵ��ԭ��SDK�Ķ��й����������ع��ܡ�

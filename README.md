# ShareSDK_DLoader #

ShareSDK官方对外提供的是静态jar包，一旦发现SDK或者服务端有bug，客户端程序就不得不重新发布版本，这可能导致很大的损失。DLoader可以在一定程度上解决了这个问题。它将ShareSDK的静态jar包制作成apk放置于互联网，客户端程序不再集成SDK的静态jar包，而只是携带用于加载apk的简单代码。这不仅极大地减少了最终程序的体积，而且一旦SDK出现问题，可以通过编译并提交修复后的apk，使快速得到修复。本文将对DLoader的使用方法列述如下：

## 目录分布 ##

clone下来后的DLoader项目包括3个目录：DLibs、Dloader和Demos。DLibs里面是ShareSDK的最新SDK，在发布此项目时，ShareSDK更新到2.6.5版本；DLoader是项目核心代码；Demos里面是ShareSDKLoader，一个演示加载ShareSDK的程序——Dloader理论上可以使用在mob的所有产品线上，但目前只有ShareSDK。

所有的项目使用Eclipse的方式组织代码，并且暂时没有迁移到AS的计划。

## 集成方法 ##

首先须要将DLoader导入Eclipse。

其次，你可以选择将DLoader做成jar包，或者跟Demo一样，直接依赖DLoader项目。我比较建议前者。

最后，像[ShareSDK的官方文档](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)描述的一样，先调用ShareSDK.init方法初始化ShareSDK，之后调用Platform的showUser或者share来完成业务。

**DLoader不使用OnekeyShare GUI库**，所以只能通过无界面接口实现业务逻辑。

## 修改代码 ##

**DLoader是开源的，你可以以任意目的修改和使用其中的代码**。在这个项目中，一般你会涉及到的修改包括：apk文件所包含的平台数量、apk文件的下载路径、更新apk文件的策略和ShareSDK类的wrapper代码。

### 修改apk文件所包含的平台数量 ###

因为DLoader提供的ShareSDK库直接来自mob官网，对于添加或者减少集成的平台数量，你可以参考[ShareSDK的官方文档](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)。但其实不外乎就是添加或者删除具体平台的jar包而已。

**只能支持ShareSDK 2.6.5以后的版本**。

### 修改apk文件的下载路径 ###

DLoader写死了apk文件的下载路径，代码的具体位置在“m.dloader.sharesdk.ShareSDK”类的“downloadSDK”方法

``` java
private static void downloadSDK(File SDKFile) {
	try {
		String urlStr = "https://github.com/ShareSDKPlatform/ShareSDK_DLoader/blob/master/DLibs/ShareSDK/ShareSDK.apk?raw=true";
		URL ourl = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) ourl.openConnection();
		conn.connect();
	………………
```

### 修改更新apk文件的策略 ###

默认情况下DLoader每次initSDK都会重新下载apk——这显示不是必要的。优化的思路包括：先判断本地时候有apk，如果没有，则下载apk；如果有，则计算本地apk的md5，然后向服务器请求下载地址。如果地址不为空，则下载新文件替换，否则不需要更新。

### 修改ShareSDK类的wrapper代码 ###

DLoader中ShareSDK的核心代码在“m.dloader.sharesdk.ShareSDK.java”中，我只是实现了一部分ShareSDK常用的功能，如果你需要更多的功能，则需要模范已经存在的代码，自行添加。DLoader的wrapper原理很简单，就是**java的反射**，利用反射理论上可以实现原生SDK的多有公开或者隐藏功能。

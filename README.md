# DLoader #

DLoader用于在运行时加载Mob不同产品SDK，相比较常规集成Mob各类SDK的方式，DLoader依赖库体积更小，完全开源，动态升级，而且可控性更高。

当前DLoader已经实现了[ShareSDK](http://sharesdk.mob.com/#/sharesdk)和[SMSSDK](http://sms.mob.com/#/sms)两个产品，理论上它可以应用在Mob的任何产品上，甚至Mob以外的产品上。

本文将对DLoader的使用方法列述如下：

## 目录分布 ##

clone下来后的DLoader项目包括3个目录：DLib、Dloader和Demo。DLib里面是ShareSDK和SMSSDK两个项目最新版本的合体APK，在发布此项目时，ShareSDK更新到2.6.6、SMSSDK更新到2.0.0；DLoader是项目核心代码；Demo里面是DLoader的演示程序，对ShareSDK和SMSSDK两个项目的主要功能进行调用。

所有的项目使用Eclipse的方式组织代码。

## 集成方法 ##

首先须要将DLoader导入Eclipse。

其次，你可以选择将DLoader做成jar包，或者跟Demo一样，直接依赖DLoader项目。我比较建议前者。

最后，像[ShareSDK](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)和[SMSSDK](http://wiki.mob.com/android-%E7%9F%AD%E4%BF%A1sdk%E9%9B%86%E6%88%90%E6%96%87%E6%A1%A3/)官方文档描述的一样，如先调用ShareSDK.init方法初始化ShareSDK，之后调用Platform的showUser或者share来完成业务；或者先调用SMSSDK.init方法初始化SMSSDK，然后new一个RegisterPage，并show出来实现短信验证码校验。

DLib中的MobLibs.apk默认集成了OnekeyShare和SMSSDK GUI两个开源GUI项目。

## 修改代码 ##

**DLoader是开源的，你可以以任意目的修改和使用其中的代码**。在这个项目中，一般你会涉及到的修改包括：apk文件所包含的平台和本地库数量、apk文件的下载路径、更新apk文件的策略和两个SDK的wrapper代码。

### apk文件所包含的平台和本地库数量 ###

因为DLoader使用的两套SDK依赖库直接来自mob官网，对于添加或者减少ShareSDK集成的平台数量，或者添加或减少SMSSDK本地库的数量，你可以参考[ShareSDK](http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97/)和[SMSSDK](http://wiki.mob.com/android-%E7%9F%AD%E4%BF%A1sdk%E9%9B%86%E6%88%90%E6%96%87%E6%A1%A3/)的官方文档。但其实不外乎就是添加或者删除具体依赖库文件而已。

**只能支持ShareSDK 2.6.5和SMSSDK2.0.0以后的版本**。

### 修改apk文件的下载路径 ###

DLoader写死了apk文件的下载路径，代码的具体位置在“m.dloader.DLoader”类的“APK_URL”字段

``` java
public class DLoader {
	private static final String APK_URL = "https://github.com/MobClub/DLoader/blob/master/DLib/MobLibs.apk?raw=true";

	………………
```

### 修改更新apk文件的策略 ###

默认情况下DLoader每次load都会重新下载apk——这显示不是必要的。优化的思路包括：先判断本地时候有apk，如果没有，则下载apk；如果有，则计算本地apk的md5，然后向服务器请求下载地址。如果地址不为空，则下载新文件替换，否则不需要更新。

### 修改两个SDK的wrapper代码 ###

DLoader中两个SDK的核心代码在“m.dloader.impl”包下，我只是实现了它们一部分常用的功能，如果你需要更多的功能，则需要模仿已经存在的代码，自行添加。DLoader的wrapper原理很简单，就是**java的反射**，利用反射理论上可以实现原生SDK的多有公开或者隐藏功能。

## 其他说明 ##

*DLoader不生产SDK，DLoader只是SDK的搬运工。*


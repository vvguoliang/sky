# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#在运行状态中出现报错现象
-keepattributes EnclosingMethod

#注解代码混淆
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keepclasseswithmembernames class * {
#    @butterknife.* <fields>;
#}

#指定代码的压缩级别
-optimizationpasses 5

#是否使用大小写混合
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

#不优化输入的类文件
-dontoptimize

#混淆时是否记录日志
-verbose

#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


#微信分享
#-keep class com.tencent.mm.**{*;}
#-keep public class com.tencent.** {*;}
#-keep class com.tencent.mm.sdk.** {*;}

#jni动态库
-keepclasseswithmembernames class * {
    native <methods>;
}

#资源文件
-keep public class com.jyh.com.jyh.R$*{
   public static final int *;
}
#gson混淆配置
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}
-dontobfuscate

#友盟
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#激光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# 保留R下面的资源
-keep class **.R$* {*;}

#芝麻信用
-keep class com.alipayzhima.**{*;}
-keep class com.android.moblie.zmxy.antgroup.creditsdk.**{*;}
-keep class com.antgroup.zmxy.mobile.android.container.**{*;}
-keep class org.json.alipayzhima.**{*;}

-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory {*;}

-keep class com.unionpay.mobile.android.**{*;}

-keep public abstract interface com.asqw.android.Listener{
public protected <methods>;
}

-keep public class com.asqw.android{
public void Start(java.lang.String);
}

-keepclasseswithmembers class * { public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {public void *(android.view.View);
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepattributes *Annotation*
#js交互
-keepattributes *JavascriptInterface*

-keep class com.jyh.com.jyh.EntityClass.**{
	*;
}

-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

 #连连Demo混淆keep规则，如果使用了Demo中工具包com.yintong.pay.utils下面的类，请对应添加keep规则，否则混下会包签名错误
-keep public class ccom.jyh.com.jyh.Again.** {
    <fields>;
    <methods>;
}
# 连连混淆keep规则，请添加
-keep class com.yintong.secure.activityproxy.PayIntro$LLJavascriptInterface{*;}

# 连连混淆keep规则
-keep public class com.jyh.com.jyh.** {
    <fields>;
    <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 支付宝
-keep class com.alipay.android.app.IAliPay{*;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.lib.ResourceMap{*;}

# Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }
-keepattributes Signature
-ignorewarnings

#Support library
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#Support v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

#Support v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

#TONGDUN
-dontwarn android.os.**
-dontwarn com.android.internal.**
-keep class cn.tongdun.android.shell.**{*;}
-keep class cn.tongdun.android.shell.common.**{*;}
-keep class cn.tongdun.android.shell.exception.**{*;}
-keep class cn.tongdun.android.shell.inter.**{*;}
-keep class cn.tongdun.android.shell.settings.**{*;}
-keep class cn.tongdun.android.shell.utils.**{*;}

#高德地图混淆
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.amap.mapcore.*{*;}
-keep class com.amap.api.trace.**{*;}
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-keep class com.amap.api.services.**{*;}
-keep class com.loc.**{*;}

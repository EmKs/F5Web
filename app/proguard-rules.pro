# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\IDE\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
#生成混淆后的映射关系
-verbose
#输出映射文件到mappingcd
-printmapping build/outputs/mapping/release/mapping.txt
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes Signature
#注解不需要混淆
-keepattributes *Annotation*
#android不做预检验，去掉可以加快混淆速度
-dontpreverify
#抛出异常时保留代码行号，
-keepattributes SourceFile,LineNumberTable


#实体类开始
-keep class com.dev.base.mvp.model.entity.** { *; }
#如果你使用了Devring中的GreenOpenHelper来创建初始化DaoMaster，那就需要把GreenDao自动生成的XXXDao忽略混淆
-keep class com.dev.base.mvp.model.db.greendao.** { *; }
#实体类结束


#RxBus开始
-keep class com.dev.base.mvp.model.bus.support.ThreadMode { *; }
-keepclassmembers class * {
    @com.dev.base.mvp.model.bus.support.Subscribe <methods>;
}
#RxBus结束


#fresco开始
-keep class com.facebook.fresco.** { *; }
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
     @com.facebook.common.internal.DoNotStrip *;
}
-keep class com.facebook.imagepipeline.gif.** { *; }
-keep class com.facebook.imagepipeline.webp.* { *; }
-keepclassmembers class * {
    native <methods>;
}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
    public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
}
#fresco结束


#glide开始
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
#glide结束


#butterknife开始
-keep class butterknife.** { *; }
  -dontwarn butterknife.internal.**
  -keep class **$$ViewBinder { *; }
  -keepclasseswithmembernames class * { @butterknife.* <fields>;}
  -keepclasseswithmembernames class * { @butterknife.* <methods>;}
#butterknife结束


# greenDAO开始
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.greenrobot.greendao.database.**
-dontwarn rx.**
# greenDAO结束


#sqlcipher数据库加密开始
-keep  class net.sqlcipher.** {*;}
-keep  class net.sqlcipher.database.** {*;}
#sqlcipher数据库加密结束


#EventBus开始
#//如果使用了EventBus processor进行加速，就必须加上这个(只要有这个注解的类和方法都不混淆，为反编译提供了便利), 如果没有用到加速，这个就不用了
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#EventBus结束


#Rxjava&RxAndroid开始
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#Rxjava&RxAndroid结束


#RxPermission开始
-keep class com.tbruyelle.rxpermissions2.** { *; }
-keep interface com.tbruyelle.rxpermissions2.** { *; }
#RxPermission结束


#Retrofit开始
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
#Retrofit结束


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public void run(...);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
  public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep public class com.ikmak.parent.R$*{
    public static final int *;
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}

-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

-keepattributes *JavascriptInterface*
-keep class **.Webview2JsInterface { *; }  # 保持WebView对HTML页面的API不被混淆
-keepclassmembers class fqcn.of.javascript.interface.for.webview {	# 保留WebView
   public *;
}

#AutoSize
-keep class me.jessyan.autosize.** { *; }
-keep interface me.jessyan.autosize.** { *; }

#BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** { *; }
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
#MaterialEditText
-keep class com.rengwuxian.materialedittext.**{ *; }
#F5Web部分不混淆代码
-keep class cn.qinguide.f5web.mvp.model.http.DownloadApiService{ *; }
-keep class cn.qinguide.f5web.mvp.model.http.ScriptApiService{ *; }
-keep class cn.qinguide.f5web.mvp.view.adapter.**{ *; }

#保留主入口和模块激活
-optimizationpasses 10
-keep class cn.qinguide.f5web.xposed.Main{ *; }
-keep class cn.qinguide.f5web.xposed.utils.ActiveUtil{ *; }
-keep class cn.qinguide.f5web.mvp.model.entity.ScriptEntity{ *; }
-keep class cn.qinguide.f5web.xposed.entity.AppInfo{ *; }
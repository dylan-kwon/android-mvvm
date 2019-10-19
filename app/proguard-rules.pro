-dontwarn kotlin.Unit
-dontwarn javax.annotation.**
-dontwarn retrofit2.Platform$Java8
-dontwarn org.jetbrains.annotations.**

-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations

# Moshi
-keep @com.squareup.moshi.JsonQualifier interface *
-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl
-keepnames @com.squareup.moshi.JsonClass class *
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker

-if @com.squareup.moshi.JsonClass class *
-keep class <1>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*
-keep class <1>_<2>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*
-keep class <1>_<2>_<3>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*$*
-keep class <1>_<2>_<3>_<4>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*$*$*
-keep class <1>_<2>_<3>_<4>_<5>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*$*$*$*
-keep class <1>_<2>_<3>_<4>_<5>_<6>JsonAdapter {
    <init>(...);
    <fields>;
}
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keepclassmembers @com.squareup.moshi.JsonClass @kotlin.Metadata class * {
    synthetic <init>(...);
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Retrofit2
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# OkHttp
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
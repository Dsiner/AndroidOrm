-keep class com.d.lib.orm.sqlite.** {*;}

# 保留Keep注解的类名和方法
-keep @com.d.lib.orm.sqlite.annotations.Entity class *
-keepclassmembers class * {
    @com.d.lib.orm.sqlite.annotations.Property *;
}

# ----- Gson -----
-keep class com.google.gson.** {*;}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature,SourceFile,LineNumberTable
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe {*;}
# Application classes that will be serialized/deserialized over Gson

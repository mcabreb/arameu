# Room entities
-keep class com.arameu.data.entity.** { *; }
-keep class com.arameu.data.dao.** { *; }
-keep class com.arameu.data.ArameuDatabase { *; }

# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.arameu.data.dto.**$$serializer { *; }
-keepclassmembers class com.arameu.data.dto.** { *** Companion; }
-keepclasseswithmembers class com.arameu.data.dto.** { kotlinx.serialization.KSerializer serializer(...); }

# Media3 / ExoPlayer
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

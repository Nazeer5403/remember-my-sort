# Keep LSPosed module entry point
-keep class eu.hxreborn.remembermysort.RememberMySortModule { *; }

# Keep all hooker classes
-keep @io.github.libxposed.api.annotations.XposedHooker class * { *; }
-keep class eu.hxreborn.remembermysort.hooks.** { *; }

# Keep all module classes
-keep class eu.hxreborn.remembermysort.** { *; }

# Keep libxposed API annotations
-keep class io.github.libxposed.api.annotations.** { *; }
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations

# Prevent R8 from removing hook methods
-keepclassmembers class * {
    @io.github.libxposed.api.annotations.BeforeInvocation <methods>;
    @io.github.libxposed.api.annotations.AfterInvocation <methods>;
}

# Keep Settings activity
-keep class eu.hxreborn.remembermysort.ui.SettingsActivity { *; }

# Xposed module class pattern
-adaptresourcefilecontents META-INF/xposed/java_init.list
-keep,allowobfuscation,allowoptimization public class * extends io.github.libxposed.api.XposedModule {
    public <init>(...);
    public void onPackageLoaded(...);
    public void onSystemServerLoaded(...);
}

# Kotlin intrinsics
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void check*(...);
    public static void throw*(...);
}
-assumenosideeffects class java.util.Objects {
    public static ** requireNonNull(...);
}

# Strip debug logs in release
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
}

# Obfuscation
-repackageclasses
-allowaccessmodification

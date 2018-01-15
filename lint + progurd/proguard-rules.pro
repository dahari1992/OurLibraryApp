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

# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.




########## explnation ########
#we want to save the method on success that in the CallBack class because it's indicates us if the action success or not
#we want to save the map that create in the MainActivity class, because we want to know all the database at start of app.


-keep class com.example.matan.library.Callback{
void onSuccess();
}
-keepclassmembernames class com.example.matan.library.MainActivity{
private Map<String, String> myMap;
}

-ignorewarnings

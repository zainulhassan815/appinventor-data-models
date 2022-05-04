# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.dreamers.postmodel.PostModel {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-keep public class com.dreamers.postmodel.Post {
    public *;
}

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/dreamers/postmodel/repack'
-flattenpackagehierarchy
-dontpreverify

-dontwarn com.google.gson.**
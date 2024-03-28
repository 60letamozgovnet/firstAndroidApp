#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_nat_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,jint n1) {
    std::string hello = "Hello from C++" + std::to_string(n1);
    return env->NewStringUTF(hello.c_str());
}
#include "java_WasmEdge.h"
#include <jni.h>
#include <string>
#include <array>
#include <wasmedge/wasmedge.h>
#include <android/log.h>

#define TAG "wasm-log"

/*
 * Class:     com_zw_app_WasmEdge
 * Method:    wasm_init
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong

JNICALL Java_com_zw_app_WasmEdge_wasm_1init
        (JNIEnv *env, jobject jobj, jstring jParamJson) {
    return 1;
}

/*
 * Class:     com_zw_app_WasmEdge
 * Method:    wasm_exec
 * Signature: (J[BLjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint

JNICALL Java_com_zw_app_WasmEdge_wasm_1exec
        (JNIEnv *env, jobject jobj, jlong jinstance,
         jbyteArray jWasmBuff, jstring jFuncName,
         jstring jJsonParam) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "exec ....");

    jsize jWasmBuff_size = env->GetArrayLength(jWasmBuff);
    jbyte *wasm_buffer = env->GetByteArrayElements(jWasmBuff, nullptr);

    jsize jFuncName_size = env->GetStringUTFLength(jFuncName);
    const char *funcName_buffer = env->GetStringUTFChars(jFuncName, nullptr);
    __android_log_print(ANDROID_LOG_INFO, TAG, "funcName: %s", funcName_buffer);

    jsize jJsonParam_size = env->GetStringUTFLength(jJsonParam);
    const char *jsonParam_buffer = env->GetStringUTFChars(jJsonParam, nullptr);

    WasmEdge_ConfigureContext *conf = WasmEdge_ConfigureCreate();
    WasmEdge_ConfigureAddHostRegistration(conf, WasmEdge_HostRegistration_Wasi);

    WasmEdge_VMContext *vm_ctx = WasmEdge_VMCreate(conf, nullptr);

    const WasmEdge_String &func_name = WasmEdge_StringCreateByCString(funcName_buffer);

    const WasmEdge_String &Str = WasmEdge_StringCreateByCString(jsonParam_buffer);
    std::array<WasmEdge_Value, 1> params{
            WasmEdge_ValueGenExternRef((void *) jsonParam_buffer)};
    std::array<WasmEdge_Value, 1> ret_val{};

    WasmEdge_Value res = WasmEdge_VMRunWasmFromBuffer(
            vm_ctx, (uint8_t *) wasm_buffer,
            jWasmBuff_size,
            func_name, params.data(), params.size(),
            ret_val.data(), ret_val.size());
    WasmEdge_Value res1 = res;

    WasmEdge_VMDelete(vm_ctx);
    WasmEdge_ConfigureDelete(conf);
    WasmEdge_StringDelete(func_name);

//    env->ReleaseByteArrayElements(image_bytes, buffer, 0);
    if (!WasmEdge_ResultOK(res)) {
        __android_log_print(ANDROID_LOG_INFO, TAG, "error");
        return -1;
    } else {
        char *chs = const_cast<char *>(WasmEdge_ResultGetMessage(res1));
        __android_log_print(ANDROID_LOG_INFO, TAG, "Error message: %s\n", chs);
    }

    const char *chs = WasmEdge_ResultGetMessage(ret_val[0]);
    __android_log_print(ANDROID_LOG_INFO, TAG, "chs %s", chs);

    return 1;
}

/*
 * Class:     com_zw_app_WasmEdge
 * Method:    wasm_release
 * Signature: (JLjava/lang/String;)I
 */
//JNIEXPORT jint
//
//JNICALL Java_com_zw_app_WasmEdge_wasm_1release
//        (JNIEnv *env, jobject jobj, jlong jinstance, jstring jJsonParam){
//    return 1;
//}

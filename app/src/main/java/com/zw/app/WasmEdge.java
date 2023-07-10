package com.zw.app;

public class WasmEdge {
    private long instance = 0;

    public long init(String jsonParam) {
        instance = wasm_init(jsonParam);
        return instance;
    }

    public int exec(byte[] wasm, String funcName, String jsonParam) {
        return wasm_exec(instance, wasm, funcName, jsonParam);
    }

    public int release(String jsonParam) {
        return wasm_release(instance, jsonParam);
    }

    private native long wasm_init(String jsonParam);

    private native int wasm_exec(long instance, byte[] wasm, String funcName, String jsonParam);

    private native int wasm_release(long instance, String jsonParam);

    static {
        System.loadLibrary("wasmedge");
    }
}

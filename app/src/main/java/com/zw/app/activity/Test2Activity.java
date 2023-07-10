package com.zw.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zw.app.R;
import com.zw.app.WasmEdge;

import java.io.File;
import java.io.FileInputStream;

public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WasmEdge edge = new WasmEdge();
                    File f = new File("/data/local/tmp/hello.wasm");
                    int len = (int) f.length();
                    byte[] wasm = new byte[len];
                    FileInputStream fileInputStream = new FileInputStream(f);
                    fileInputStream.read(wasm);
                    String funcName = "wasm_exec";
                    String jsonParam = "12345";
                    edge.exec(wasm, funcName, jsonParam);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
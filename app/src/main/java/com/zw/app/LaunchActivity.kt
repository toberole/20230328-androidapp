package com.zw.app

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.zw.app.activity.Test1Activity
import com.zw.app.activity.Test2Activity
import com.zw.app.activity.WasmEdgeActivity

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    private var btn_test1: Button? = null

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            ), 123
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        requestPermissions()

        btn_test1 = findViewById(R.id.btn_test1)
        btn_test1?.setOnClickListener(this)

        btn_test1 = findViewById(R.id.btn_wasm)
        btn_test1?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                var intent = Intent(MainActivity@ this, Test1Activity::class.java)
                startActivity(intent)
            }

            R.id.btn_wasm -> {
                var intent = Intent(MainActivity@ this, Test2Activity::class.java)
                startActivity(intent)
            }
        }
    }
}
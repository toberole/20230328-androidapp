package com.zw.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.zw.app.activity.Test1Activity

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    private var btn_test1: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        btn_test1 = findViewById(R.id.btn_test1)
        btn_test1?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                var intent = Intent(MainActivity@ this, Test1Activity::class.java)
                startActivity(intent)
            }
        }
    }
}
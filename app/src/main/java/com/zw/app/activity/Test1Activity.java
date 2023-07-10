package com.zw.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.zw.app.R;
import com.zw.app.view.MagicView;

import java.util.ArrayList;
import java.util.List;

public class Test1Activity extends AppCompatActivity {
    private MagicView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        myView = findViewById(R.id.myView);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int Height = myView.getHeight();
                int width = myView.getWidth();
                getWindow().getDecorView().getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);//一定要移除,因为onLayout()会执行多次,如果不移除的话 也会被回调多次,而且值还不一样
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Point> points = new ArrayList<>();
                points.add(new Point(50, 50));
                points.add(new Point(100, 100));
                points.add(new Point(150, 150));
                points.add(new Point(200, 200));
                points.add(new Point(250, 250));
                points.add(new Point(300, 300));
                points.add(new Point(350, 350));
                points.add(new Point(400, 400));
                points.add(new Point(450, 450));
                points.add(new Point(500, 500));
                points.add(new Point(550, 550));
                points.add(new Point(600, 600));
                while (true) {
//                    myView.u(points);
                }
            }
        }).start();
    }

    private void test1() {
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
        Log.i("TAG", "test1test1´");
    }
}
package com.jian.android.screencut;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button mScreenCut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreenCut = (Button) findViewById(R.id.btn_screen_cut);

        mScreenCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takeScreenShot(MainActivity.this);

                Intent intent=new Intent(MainActivity.this,ScreenCutActivity.class);
                startActivity(intent);
            }
        });
    }

    public  void takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        //获取actiobBar的高度
        float actionBarHeight=activity.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize})
                .getDimension(0,0);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(bitmap, 0, statusBarHeight+(int)actionBarHeight, width, height
                - statusBarHeight-(int)actionBarHeight);
        view.destroyDrawingCache();

        //将bitmap存入本地文件

        //获取系统缓存文件
        File cacheFile=new File(getCacheDir(),"ScreenCut");

        FileOutputStream out=null;
        try {
            out=new FileOutputStream(cacheFile);
            b.compress(Bitmap.CompressFormat.PNG,90,out);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

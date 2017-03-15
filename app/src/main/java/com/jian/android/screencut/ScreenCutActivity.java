package com.jian.android.screencut;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ScreenCutActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView mRecycler;

    private int[] iconIds;
    private ArrayList<IconBean> mDatas;
    private ImageView iv_icon_show;
    private EditText et_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_cut);

        imageView= (ImageView) findViewById(R.id.iv_image);
        iv_icon_show = (ImageView) findViewById(R.id.iv_icon_show);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        et_input = (EditText) findViewById(R.id.et_input);

        //禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        File cacheFile=new File(getCacheDir(),"ScreenCut");

        if (cacheFile.exists()){

            Bitmap bm = BitmapFactory.decodeFile(getCacheDir()+"//ScreenCut");

            imageView.setImageBitmap(bm);
        }else {
            Toast.makeText(this,"加载图片失败",Toast.LENGTH_SHORT).show();
        }

        initData();

        initView();

    }

    public void initData(){

        iconIds = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,
                R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l};

        String[] iconDes=new String[]{"我读书少，你可别骗我","知道真相的我眼泪流下来","感觉不会再爱了"
                ,"分分钟又涨姿势了","我裤子都脱了，你就给我看这个","心里默默点个赞","该吃药了","不明觉厉"
                ,"细思极恐","你懂得", "不管你信不信，反正我信了", "我和小伙伴们都惊呆了"};

        mDatas = new ArrayList<IconBean>();

        for (int i=0;i<iconIds.length;i++) {

            IconBean mIcon = new IconBean();

            if (i==0){
                mIcon.isSelected=true;
            }else {
                mIcon.isSelected=false;
            }

            mIcon.mIconId=iconIds[i];
            mIcon.des=iconDes[i];

            mDatas.add(mIcon);
        }
    }

    public void initView(){

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(new MyAdapter(mDatas,iv_icon_show,et_input));
    }

}

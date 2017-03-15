package com.jian.android.screencut;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ScreenCutActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView mRecycler;

    private int[] iconIds;
    private ArrayList<IconBean> mDatas;
    private ImageView iv_icon_show;
    private EditText et_input;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_cut);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ShareSDK.initSDK(this);

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


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("Share");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.share:

                showShare();

                break;

            default:
                break;
        }

        return true;
    }
}

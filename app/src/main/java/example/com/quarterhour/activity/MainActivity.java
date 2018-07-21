package example.com.quarterhour.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hjm.bottomtabbar.BottomTabBar;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import example.com.quarterhour.MyApp;
import example.com.quarterhour.R;
import example.com.quarterhour.fragment.CrossTalk;
import example.com.quarterhour.fragment.Referrals;
import example.com.quarterhour.fragment.Videos;
import example.com.quarterhour.widget.SlidingMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomTabBar tabBar;
    private SimpleDraweeView userT;
    private SlidingMenu activityMain;
    /**
     * 推荐
     */
    private TextView title;
    private SimpleDraweeView headIcon;
    private AutoRelativeLayout line1;
    private AutoRelativeLayout line2;
    private AutoRelativeLayout line3;
    private AutoRelativeLayout line4;
    private ImageView riye;
    private ImageView switchBtn;
    private ImageView edtBtn;
    private boolean flag = true;
    private AutoLinearLayout myworks;
    private AutoLinearLayout setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tabBar = (BottomTabBar) findViewById(R.id.tabBar);
        tabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)
                .setFontSize(12)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.RED, Color.BLACK)
                .addTabItem("推荐", R.mipmap.raw_1500085367, R.mipmap.raw_1500083878, Referrals.class)
                .addTabItem("段子", R.mipmap.raw_1500085899, R.mipmap.raw_1500085327, CrossTalk.class)
                .addTabItem("视频", R.mipmap.raw_1500086067, R.mipmap.raw_1500083686, Videos.class)
                .setTabBarBackgroundColor(Color.WHITE)
                .isShowDivider(false);
        tabBar.setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
            @Override
            public void onTabChange(int position, String name) {
                title.setText(name);
            }
        });
        userT = (SimpleDraweeView) findViewById(R.id.user_t);
        userT.setOnClickListener(this);
        activityMain = (SlidingMenu) findViewById(R.id.activity_main);
        title = (TextView) findViewById(R.id.title);
        headIcon = (SimpleDraweeView) findViewById(R.id.head_icon);
        line1 = (AutoRelativeLayout) findViewById(R.id.line1);
        line1.setOnClickListener(this);
        line2 = (AutoRelativeLayout) findViewById(R.id.line2);
        line2.setOnClickListener(this);
        line3 = (AutoRelativeLayout) findViewById(R.id.line3);
        line3.setOnClickListener(this);
        line4 = (AutoRelativeLayout) findViewById(R.id.line4);
        line4.setOnClickListener(this);
        riye = (ImageView) findViewById(R.id.riye);
        switchBtn = (ImageView) findViewById(R.id.switch_btn);
        switchBtn.setOnClickListener(this);
        edtBtn = (ImageView) findViewById(R.id.edt_btn);
        edtBtn.setOnClickListener(this);
        myworks = (AutoLinearLayout) findViewById(R.id.myworks);
        myworks.setOnClickListener(this);
        setting = (AutoLinearLayout) findViewById(R.id.setting);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.user_t:
                activityMain.toggle();
                break;
            case R.id.line1:
                Intent intent1 = new Intent(MyApp.context, MyFocusActivity.class);
                startActivity(intent1);
                break;
            case R.id.line2:
                Intent intent2 = new Intent(MyApp.context, MyCollectActivity.class);
                startActivity(intent2);
                break;
            case R.id.line3:
                Intent intent3 = new Intent(MyApp.context, SearchActivity.class);
                startActivity(intent3);
                break;
            case R.id.line4:
                Intent intent4 = new Intent(MyApp.context, MessageActivity.class);
                startActivity(intent4);
                break;
            case R.id.myworks:
                Intent intent5 = new Intent(MyApp.context, MyWorksActivity.class);
                startActivity(intent5);
                break;
            case R.id.setting:
                Intent intent6 = new Intent(MyApp.context, SettingActivity.class);
                startActivity(intent6);
                break;
            case R.id.switch_btn:
                if (flag) {
                    switchBtn.setImageResource(R.mipmap.kaiguan2);
                    riye.setImageResource(R.mipmap.rijian);
                    flag = false;
                } else {
                    switchBtn.setImageResource(R.mipmap.kaiguan);
                    riye.setImageResource(R.mipmap.yejian);
                    flag = true;
                }
                break;
            case R.id.edt_btn:
                break;
        }
    }
}

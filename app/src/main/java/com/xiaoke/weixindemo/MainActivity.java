package com.xiaoke.weixindemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentPagerAdapter fragmentPagerAdapter;//fragment的数据适配器
    private ViewPager viewPager;//原生viewpager
    private List<Fragment> dataList = null;//存放listView的集合

    private TextView tv_address;
    private TextView tv_chat;
    private TextView tv_me;

    private BadgeView badgeView;//开源框架badgeView
    private LinearLayout ll_layout;

    private View iv_line;//标题指示线的图片
    private int mScreen1_3;//记录屏幕宽度的1/3
    public int mCurrentPageIndex;//记录当前是那个界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_me = (TextView) findViewById(R.id.tv_my);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        //iv_line = (ImageView) findViewById(R.id.iv_line);
        iv_line = (View) findViewById(R.id.view_line);

        //给标题设置点击的侦听事件
        tv_address.setOnClickListener(this);
        tv_chat.setOnClickListener(this);
        tv_me.setOnClickListener(this);

        //初始化视图
        initView();
        //顶部标题和指针
        initTab();

    }

    private void initTab() {
        /**
         * 获取屏幕宽度的1/3
         */

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

        mScreen1_3 = outMetrics.widthPixels / 3;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iv_line.getLayoutParams();
        lp.width = mScreen1_3;//
        iv_line.setLayoutParams(lp);//给线宽度重新设置参数

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {

                Log.v("MainActivity", "position---->" + position + "positionOffset---->" + positionOffset + "mcurrent---->" + mCurrentPageIndex);
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_line.getLayoutParams();
//
//                if (mCurrentPageIndex == 0 && position == 0) {//0-->1
//                    layoutParams.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex * mScreen1_3);
//                } else if (mCurrentPageIndex == 1 && position == 0)//1-->0
//                {
//                    layoutParams.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset-1) * mScreen1_3);
//                } else if (mCurrentPageIndex == 1 && position == 1)//1-->2
//                {
//                    layoutParams.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset) * mScreen1_3);
//                } else if (mCurrentPageIndex == 2 && position == 1)//2-->1
//                {
//                    layoutParams.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset-1) * mScreen1_3);
//                }
//
//                iv_line.setLayoutParams(layoutParams);

                //第二种方法，使用属性动画
                int targetPosition = mScreen1_3 * position + positionOffsetPx / 3;
                ViewPropertyAnimator.animate(iv_line).translationX(targetPosition).setDuration(0);

            }

            private void resetTextView() {
                tv_address.setTextColor(Color.BLACK);
                tv_chat.setTextColor(Color.BLACK);
                tv_me.setTextColor(Color.BLACK);
            }

            @Override
            public void onPageSelected(int position) {
                //把viewpage当前选中的页数赋值给定义的变量
                mCurrentPageIndex = position;
                resetTextView();
                lightAndScaleTabTitle();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }

    /**
     * 根据viewpager的当前页数，使当前页的标题变颜色和变大
     */
    private void lightAndScaleTabTitle() {
        int position = viewPager.getCurrentItem();
        switch (position) {
            case 0:
                if (badgeView != null) {
                    ll_layout.removeView(badgeView);
                }
                badgeView = new BadgeView(MainActivity.this);
                badgeView.setBadgeCount(666666);
                //badgeView.setBadgeMargin(60);
                //badgeView.setBadgeMargin(600,10,10,10);
                ll_layout.addView(badgeView);
                tv_address.setTextColor(Color.GREEN);
                break;
            case 1:
                tv_chat.setTextColor(Color.GREEN);
                break;
            case 2:
                tv_me.setTextColor(Color.GREEN);
                break;
        }
        ViewPropertyAnimator.animate(tv_address).scaleX(position == 0 ? 1.3f : 1.0f).setDuration(300);
        ViewPropertyAnimator.animate(tv_address).scaleY(position == 0 ? 1.3f : 1.0f).setDuration(300);
        ViewPropertyAnimator.animate(tv_chat).scaleX(position == 1 ? 1.3f : 1.0f).setDuration(300);
        ViewPropertyAnimator.animate(tv_chat).scaleY(position == 1 ? 1.3f : 1.0f).setDuration(300);
        ViewPropertyAnimator.animate(tv_me).scaleX(position == 2 ? 1.3f : 1.0f).setDuration(300);
        ViewPropertyAnimator.animate(tv_me).scaleY(position == 2 ? 1.3f : 1.0f).setDuration(300);

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        dataList = new ArrayList<Fragment>();
        //把添加的三个Fragemnt放入集合中
        dataList.add(new Fragment1());
        dataList.add(new Fragment2());
        dataList.add(new Fragment3());

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return dataList.get(i);
            }

            @Override
            public int getCount() {
                return dataList.size();
            }
        };
        //给veiwpager设置数据适配器
        viewPager.setAdapter(fragmentPagerAdapter);
        lightAndScaleTabTitle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_address:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_chat:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_my:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}

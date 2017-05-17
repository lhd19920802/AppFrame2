package com.lhd.appframe2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lhd.appframe2.fragment.ContentFragment;
import com.lhd.appframe2.fragment.LeftMenuFragment;
import com.lhd.appframe2.utils.DensityUtil;

public class MainActivity extends SlidingFragmentActivity
{

    private static final String LEFTMENU_TAG = "leftmenu_tag";
    private static final String MAIN_TAG = "main_tag";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //2.设置左侧菜单的页面
        setBehindContentView(R.layout.leftmenu);

        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        //        slidingMenu.setSecondaryMenu(R.layout.rightmenu);

        //4.设置模式：左侧+主页面；左侧+主页+右侧；主页+右侧
        slidingMenu.setMode(SlidingMenu.LEFT);

        //5.设置滑动区域：全屏滑动，边缘滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);


        //6.设置主页面占200dp位置
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this, 200));


        initFragment();
    }

    private void initFragment()
    {
        //1.FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft  = fm.beginTransaction();
        //3.替换Fragment
        ft.replace(R.id.fl_leftmenu,new LeftMenuFragment(), LEFTMENU_TAG);
        ft.replace(R.id.fl_main, new ContentFragment(), MAIN_TAG);

        //4.事务提交
        ft.commit();
    }

    public LeftMenuFragment getLeftMenuFragment()
    {

        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }
}

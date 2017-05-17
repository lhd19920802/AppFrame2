package com.lhd.appframe2.pager.menupager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lhd.appframe2.base.MenuDetailBasePager;


/**
 *
 */
public class InteracMenuDetailPager extends MenuDetailBasePager
{
    private TextView textView;

    public InteracMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        textView = new TextView(mActivity);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        System.out.println("互动详情页面数据被初始化了...");
        textView.setText("互动详情页面的内容");
    }
}

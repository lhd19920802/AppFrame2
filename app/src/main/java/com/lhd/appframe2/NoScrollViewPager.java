package com.lhd.appframe2;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lihuaidong on 2017/5/17 10:04.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：自定义ViewPager屏蔽ViewPager的滑动
 */
public class NoScrollViewPager extends ViewPager
{
    public NoScrollViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return true;

    }
}

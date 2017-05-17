package com.lhd.appframe2.utils;

import android.content.Context;

/**
 * Created by lihuaidong on 2017/5/16 9:29.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：屏幕适配工具类
 */
public class DensityUtil
{
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

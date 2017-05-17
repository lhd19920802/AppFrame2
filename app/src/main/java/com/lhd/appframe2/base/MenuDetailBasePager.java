package com.lhd.appframe2.base;

import android.app.Activity;
import android.view.View;

/**
 *
 */
public abstract class MenuDetailBasePager
{


    /**
     * 上下文
     */
    public final Activity mActivity;

    /**
     * 根View
     */
    public View rootView;

    public MenuDetailBasePager(Activity activity){
        this.mActivity = activity;
        rootView = initView();
    }

    /**
     * 初始化视图
     * @return
     */
    public abstract View initView();

    /**
     * 当孩子需要请求网络数据，或者数据初始化的时候，重新该方法
     */
    public void initData(){

    }


}

package com.lhd.appframe2.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.lhd.appframe2.base.BaseFragment;
import com.lhd.appframe2.domain.NewsCenterBean;

import java.util.List;

/**
 * Created by lihuaidong on 2017/5/17 9:13.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：左侧菜单的fragment
 */
public class LeftMenuFragment extends BaseFragment
{

    private TextView textView;
    @Override
    public View initView()
    {
        textView = new TextView(mContext);
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        return textView;

    }

    @Override
    public void initData()
    {
        super.initData();
        textView.setText("leftMenu");
    }

    public void setData(List<NewsCenterBean.DataEntity> leftMenudata)
    {
        
    }
}

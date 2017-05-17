package com.lhd.appframe2.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lhd.appframe2.MainActivity;
import com.lhd.appframe2.R;


/**
 * 5个页面的基类
 */
public class BasePager
{

    public TextView tv_base_title;
    public ImageButton ib_menu;
    public FrameLayout fl_base_content;
    public ImageButton ib_switche_mode;

    /**
     * 上下文
     */
    public final Activity mActivity;

    /**
     * 根View
     */
    public View rootView;

    public BasePager(Activity activity)
    {
        this.mActivity = activity;
        rootView = initView();
    }

    /**
     * 初始化视图
     *
     * @return
     */
    private View initView()
    {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tv_base_title = (TextView) view.findViewById(R.id.tv_base_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_base_content = (FrameLayout) view.findViewById(R.id.fl_base_content);
        ib_switche_mode = (ImageButton) view.findViewById(R.id.ib_switche_mode);

        ib_menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //2.SlidingMenu收起来
                MainActivity mainActivity = (MainActivity) mActivity;
                mainActivity.getSlidingMenu().toggle();//关-开；开-关
            }
        });
        return view;
    }

    /**
     * 当孩子需要请求网络数据，或者数据初始化的时候，重新该方法
     */
    public void initData()
    {

    }


}

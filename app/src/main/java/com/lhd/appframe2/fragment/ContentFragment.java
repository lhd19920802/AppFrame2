package com.lhd.appframe2.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lhd.appframe2.MainActivity;
import com.lhd.appframe2.R;
import com.lhd.appframe2.base.BaseFragment;
import com.lhd.appframe2.base.BasePager;
import com.lhd.appframe2.pager.GovaffairPager;
import com.lhd.appframe2.pager.HomePager;
import com.lhd.appframe2.pager.NewCenterPager;
import com.lhd.appframe2.pager.SettingPager;
import com.lhd.appframe2.pager.SmartServicePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihuaidong on 2017/5/17 9:13.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：内容的fragment
 */
public class ContentFragment extends BaseFragment
{
    private ViewPager vp_content_fragment;
    private RadioGroup rg_content_fragment;
    private List<BasePager> pagers;
    @Override
    public View initView()
    {
        View view = View.inflate(mContext, R.layout.content_fragment, null);
        vp_content_fragment = (ViewPager) view.findViewById(R.id.vp_content_fragment);

        rg_content_fragment = (RadioGroup) view.findViewById(R.id.rg_content_fragment);

        initPagers();
        rg_content_fragment.check(R.id.rb_home);

        rg_content_fragment.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        return view;

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener

    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch (checkedId)

            {
                case R.id.rb_home:
                    vp_content_fragment.setCurrentItem(0,false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_newscenter:
                    vp_content_fragment.setCurrentItem(1,false);
                    isEnableSlidingMenu(true);
                    break;
                case R.id.rb_smartservice:
                    vp_content_fragment.setCurrentItem(2,false);
                    isEnableSlidingMenu(false);

                    break;
                case R.id.rb_govaffair:
                    vp_content_fragment.setCurrentItem(3,false);
                    isEnableSlidingMenu(false);

                    break;
                case R.id.rb_setting:
                    vp_content_fragment.setCurrentItem(4,false);
                    isEnableSlidingMenu(false);

                    break;
            }
        }
    }

    /**
     * 是否让SlidingMenu可以滑动 是否滑出左侧菜单
     *
     * @param isEnableSlidingMenu
     */
    private void isEnableSlidingMenu(boolean isEnableSlidingMenu)
    {
        MainActivity mainActivity = (MainActivity) mContext;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if (isEnableSlidingMenu)
        {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
        else
        {
            //不可以滑动
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    private void initPagers()
    {
        pagers = new ArrayList<>();
        pagers.add(new HomePager(getActivity()));
        pagers.add(new NewCenterPager(getActivity()));
        pagers.add(new SmartServicePager(getActivity()));
        pagers.add(new GovaffairPager(getActivity()));
        pagers.add(new SettingPager(getActivity()));

        vp_content_fragment.setAdapter(new MyPagerAdapter());
    }


    @Override
    public void initData()
    {
        super.initData();

    }

    class MyPagerAdapter extends PagerAdapter

    {
        @Override
        public int getCount()
        {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            BasePager basePager = pagers.get(position);
            basePager.initData();
            container.addView(basePager.rootView);
            return basePager.rootView;
        }
    }
}

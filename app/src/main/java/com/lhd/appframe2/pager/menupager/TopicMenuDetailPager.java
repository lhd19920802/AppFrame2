package com.lhd.appframe2.pager.menupager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lhd.appframe2.MainActivity;
import com.lhd.appframe2.R;
import com.lhd.appframe2.base.MenuDetailBasePager;
import com.lhd.appframe2.domain.NewsCenterBean;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TopicMenuDetailPager extends MenuDetailBasePager
{

    /**
     * 页签页面的数据
     */
    private final List<NewsCenterBean.DataEntity.ChildrenEntity> children;

    /**
     * 页签页面的集合-视图的集合
     */
    private List<MenuDetailBasePager> tabDetailPagers;

    @ViewInject(R.id.vp_news_menu_detail)
    private ViewPager vp_news_menu_detail;





    public TopicMenuDetailPager(Activity activity, List<NewsCenterBean.DataEntity.ChildrenEntity> children) {
        super(activity);
        this.children = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.topic_menu_detailpager,null);
        x.view().inject(this,view);
        return view;
    }

    @Event(value = R.id.ib_next_tab)
    private void onNextTab(View view){
        vp_news_menu_detail.setCurrentItem(vp_news_menu_detail.getCurrentItem() + 1);
    }
    @Override
    public void initData() {
        super.initData();
        System.out.println("新闻详情页面数据被初始化了...");
        tabDetailPagers = new ArrayList<>();
//        for(int i=0;i<children.size();i++){//12
//            //根据数据创建多少个页面
//            tabDetailPagers.add(new TabDetailPager(mActivity,children.get(i)));
//        }


        //设置适配器
        NewsMenuDetailPagerAdapter mAdapter  = new NewsMenuDetailPagerAdapter();

        //设置ViewPager的适配器
        vp_news_menu_detail.setAdapter(mAdapter);

//        //TabLayout关联ViewPager
//        tabLayout.setupWithViewPager(vp_news_menu_detail);
//        //一定要设置设置滚动模式
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);



//        tabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        //设置ViewPager




        vp_news_menu_detail.setOnPageChangeListener(new MyPageChangeListener());


        //自定义Table布局要结合适配器中的getTabView方法
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(mAdapter.getTabView(i));
//        }


    }

    /**
     * 是否让SlidingMenu可以滑动
     * @param isEnableSlidingMenu
     */
    private void isEnableSlidingMenu(boolean isEnableSlidingMenu) {
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if(isEnableSlidingMenu){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            //不可以滑动
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position==0){
                //SlidingMenu可以从左到右的滑动
                isEnableSlidingMenu(true);
            }else{
                //SlidingMenu不可以从左到右的滑动
                isEnableSlidingMenu(false);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class NewsMenuDetailPagerAdapter extends PagerAdapter
    {

        @Override
        public CharSequence getPageTitle(int position) {
            return  children.get(position).getTitle();
        }

        /**
         * 自定义的Tab效果的时候，就需要重写该方法
         * @param position
         * @return
         */
//        public View getTabView(int position){
//            View view = LayoutInflater.from(mActivity).inflate(R.layout.tab_item, null);
//            TextView tv= (TextView) view.findViewById(R.id.textView);
//            tv.setText(children.get(position).getTitle());
//            ImageView img = (ImageView) view.findViewById(R.id.imageView);
//            img.setImageResource(R.drawable.dot_focus);
//            return view;
//        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MenuDetailBasePager detailBasePager = tabDetailPagers.get(position);
            detailBasePager.initData();
            container.addView(detailBasePager.rootView);
            return  detailBasePager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

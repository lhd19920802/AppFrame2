package com.lhd.appframe2.pager;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lhd.appframe2.MainActivity;
import com.lhd.appframe2.base.BasePager;
import com.lhd.appframe2.base.MenuDetailBasePager;
import com.lhd.appframe2.domain.NewsCenterBean;
import com.lhd.appframe2.fragment.LeftMenuFragment;
import com.lhd.appframe2.pager.menupager.InteracMenuDetailPager;
import com.lhd.appframe2.pager.menupager.NewsMenuDetailPager;
import com.lhd.appframe2.pager.menupager.PhotosMenuDetailPager;
import com.lhd.appframe2.pager.menupager.TopicMenuDetailPager;
import com.lhd.appframe2.utils.CacheUtils;
import com.lhd.appframe2.utils.Url;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**

 */
public class NewCenterPager extends BasePager
{

    private static final String TAG = NewCenterPager.class.getSimpleName();

    /**
     * 左侧菜单对应的数据
     */
    private List<NewsCenterBean.DataEntity> leftMenudata;

    /**
     * 左侧菜单对应的详情页面
     */
    private List<MenuDetailBasePager> detailBasePagers;

    /**
     * 开始联网请求的时间
     */

    public NewCenterPager(Activity activity)
    {
        super(activity);
    }

    @Override
    public void initData()
    {
        super.initData();

        //把按钮显示出来
        ib_menu.setVisibility(View.VISIBLE);

        //设置标题
        tv_base_title.setText("新闻");
        //设置主页的内容
        System.out.println("新闻中心的数据被初始化了...");
        TextView textView = new TextView(mActivity);
        textView.setText("新闻中心的内容。。。");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        //把当前TextView添加到FrameLayout中
        fl_base_content.addView(textView);

        String saveJson = CacheUtils.getString(mActivity, Url.NEWS_URL);
        if (!TextUtils.isEmpty(saveJson))
        {
            processData(saveJson);
        }
//
//
//        //联网请求数据
//        startTime = SystemClock.uptimeMillis();
        getDataFromNetByVolley();
        //        getDataFromNet();
        //        getDataFromNet2();

    }

    private void getDataFromNetByVolley()
    {
                RequestQueue queue = Volley.newRequestQueue(mActivity);

        StringRequest request = new StringRequest(Request.Method.GET, Url.NEWS_URL, new
                Response.Listener<String>()
        {
            @Override
            public void onResponse(String result)
            {
                long endTime = SystemClock.uptimeMillis();
                Log.e(TAG, "使用Volley请求成功==" + result);
                Log.e(TAG, "使用Volley请求成功==线程名称==" + Thread.currentThread().getName());
                //数据缓存
                CacheUtils.putStrig(mActivity, Url.NEWS_URL, result);
                processData(result);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "使用Volley请求失败==" + error);

            }
        })
        {

            //解决乱码问题
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response)
            {
                try
                {
                    String parsed = new String(response.data, "UTF-8");

                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));

                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);

    }
//
//    private void getDataFromNet2()
//    {
//        RequestParams params = new RequestParams("http://api.bilibili"
//                                                 + ".com/online_list?_device=android&platform=android&typeid=13&sign=a520d8d8f7a7240013006e466c8044f7");
//        x.http().get(params, new Callback.CommonCallback<String>()
//        {
//            @Override
//            public void onSuccess(String result)
//            {
//
//                TestDemo testDemo = parsedJson2(result);
//
//
//                Log.e(TAG, "手动解析数据请求成功==" + result);
//                Log.e(TAG, "手动解析成功==" + testDemo.getList().get(1).getTitle());
//
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback)
//            {
//                Log.e(TAG, "手动解析-onError==" + isOnCallback);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex)
//            {
//                Log.e(TAG, "手动解析-onCancelled==");
//            }
//
//            @Override
//            public void onFinished()
//            {
//                Log.e(TAG, "手动解析-onFinished==");
//
//            }
//        });
//    }
//
//    private TestDemo parsedJson2(String json)
//    {
//        TestDemo testDemo = new TestDemo();
//        try
//        {
//
//            JSONObject jsonObject = new JSONObject(json);
//            int code = jsonObject.optInt("code");
//            testDemo.setCode(code);
//            JSONObject listObject = jsonObject.optJSONObject("list");
//            if (listObject != null)
//            {
//                List<TestDemo.ListEntity> list = new ArrayList<>();
//                testDemo.setList(list);//把list数据加入到Bean对象中
//                for (int i = 0; i < 12; i++)
//                {
//
//                    JSONObject dataobject = listObject.optJSONObject("" + i);
//                    if (dataobject != null)
//                    {
//                        TestDemo.ListEntity listEntity = new TestDemo.ListEntity();
//
//                        String aid = dataobject.optString("aid");
//                        listEntity.setAid(aid);
//                        String author = dataobject.optString("author");
//                        listEntity.setAuthor(author);
//                        String create = dataobject.optString("create");
//                        listEntity.setCreate(create);
//                        String description = dataobject.optString("description");
//                        listEntity.setDescription(description);
//                        String duration = dataobject.optString("duration");
//                        listEntity.setDuration(duration);
//                        String pic = dataobject.optString("pic");
//                        listEntity.setPic(pic);
//                        String title = dataobject.optString("title");
//                        listEntity.setTitle(title);
//                        String typename = dataobject.optString("typename");
//                        listEntity.setTypename(typename);
//                        int mid = dataobject.optInt("mid");
//                        listEntity.setMid(mid);
//                        //添加到集合中
//                        list.add(listEntity);
//
//
//                    }
//
//                }
//
//            }
//
//
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//        return testDemo;
//    }
//
//
//    private void getDataFromNet()
//    {
//        RequestParams params = new RequestParams(Url.NEWSCENTER_URL);
//        x.http().get(params, new Callback.CommonCallback<String>()
//        {
//            @Override
//            public void onSuccess(String result)
//            {
//
//                long endTime = SystemClock.uptimeMillis();
//                Long distanceTime = endTime - startTime;
//                System.out.println("xUtils3联网请求文本信息所花的时间==" + distanceTime);
//                Log.e(TAG, "xUtils3联网请求文本信息所花的时间==" + distanceTime);
//
//                Log.e(TAG, "请求成功==" + result);
//                Log.e(TAG, "请求成功==线程名称==" + Thread.currentThread().getName());
//                //数据缓存
//                CacheUtils.putString(mActivity, Url.NEWSCENTER_URL, result);
//                processData(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback)
//            {
//                Log.e(TAG, "onError==" + isOnCallback);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex)
//            {
//                Log.e(TAG, "onCancelled==" + cex);
//            }
//
//            @Override
//            public void onFinished()
//            {
//                Log.e(TAG, "onFinished==");
//
//            }
//        });
//    }
//
    /**
     * 解析数据和绑定数据到控件上
     *
     * @param json
     */
    private void processData(String json)
    {

        NewsCenterBean centerBean = parsedJson(json);
        //把数据传递到左侧菜单
        leftMenudata = centerBean.getData();
        Log.e(TAG, "使用gson解析成功了==" + centerBean.getData().get(0).getChildren().get(2).getTitle());
        //把四个页面添加到新闻中心这里
        MainActivity mainActivity = (MainActivity) mActivity;
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(mainActivity, leftMenudata.get(0)
                .getChildren()));//新闻详情页面
        detailBasePagers.add(new TopicMenuDetailPager(mainActivity, leftMenudata.get(0)
                .getChildren()));//专题详情页面
        detailBasePagers.add(new PhotosMenuDetailPager(mainActivity));//图组详情页面
        detailBasePagers.add(new InteracMenuDetailPager(mainActivity));//互动详情页面


        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(leftMenudata);


        //        switchPager(0);


    }

    /**
     * 解析json数据
     *
     * @param json
     * @return
     */
    private NewsCenterBean parsedJson(String json)
    {
        return new Gson().fromJson(json, NewsCenterBean.class);
    }
//
//    /**
//     * 根据指定的位置切换到不同的页面
//     *
//     * @param position
//     */
//    public void switchPager(int position)
//    {
//
//        //设置标题
//        tv_base_title.setText(leftMenudata.get(position).getTitle());
//
//        //得到页面
//        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
//        View rootView = detailBasePager.rootView;//代表不同的详情页面的视图
//        detailBasePager.initData();//初始化数据
//
//        fl_base_content.removeAllViews();//移除之前的视图
//
//        fl_base_content.addView(rootView);
//
//
//        if (position == 2)
//        {
//            //按钮显示
//            ib_switche_mode.setVisibility(View.VISIBLE);
//            ib_switche_mode.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//
//                    //得到图组对象
//                    PhotosMenuDetailPager detailPager = (PhotosMenuDetailPager) detailBasePagers.get(2);
//                    //调用里面的切换方法
//                    detailPager.switchListAndGrid(ib_switche_mode);
//
//                }
//            });
//        }
//        else
//        {
//            //隐藏按钮
//            ib_switche_mode.setVisibility(View.GONE);
//        }
//
//
//    }
}

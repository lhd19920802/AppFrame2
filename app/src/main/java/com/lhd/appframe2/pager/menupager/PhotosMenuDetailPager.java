package com.lhd.appframe2.pager.menupager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.lhd.appframe2.R;
import com.lhd.appframe2.base.MenuDetailBasePager;
import com.lhd.appframe2.domain.PhotosMenuDetailBean;
import com.lhd.appframe2.utils.CacheUtils;
import com.lhd.appframe2.utils.Url;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 */
public class PhotosMenuDetailPager extends MenuDetailBasePager
{

    private static final String TAG = PhotosMenuDetailPager.class.getSimpleName();
    @ViewInject(R.id.listview)
    private ListView listview;

    @ViewInject(R.id.gridview)
    private GridView gridview;



    /**
     * 数据列表
     */
    private List<PhotosMenuDetailBean.DataEntity.NewsEntity> news;



    public PhotosMenuDetailPager(Activity activity)
    {
        super(activity);
//        bitmapCacheUtils = new BitmapCacheUtils(mActivity, handler);
    }

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.photos_pager, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData()
    {
        super.initData();
        System.out.println("图组详情页面数据被初始化了...");
        String saveJson = CacheUtils.getString(mActivity, Url.PHOTOS_URL);
        if (!TextUtils.isEmpty(saveJson))
        {
            processData(saveJson);
        }

        //联网请求数据
        getDataFromNet();

    }

    private void getDataFromNet()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        final StringRequest request = new StringRequest(Request.Method.GET, Url.PHOTOS_URL, new
                Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.e(TAG, "请求成功了==" + response);
                CacheUtils.putStrig(mActivity, Url.PHOTOS_URL, response);
                processData(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "请求失败了==" + error);
            }
        })
        {
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

//        VolleyManager.addRequest(request, "photos");
        requestQueue.add(request);
    }

    /**
     * 解析和显示数据
     *
     * @param json
     */
    private void processData(String json)
    {

        PhotosMenuDetailBean detailBean = parsedJson(json);

        System.out.println("title==========" + detailBean.getData().getNews().get(0).getTitle());

        //设置ListView的适配器
        news = detailBean.getData().getNews();
        isListViewShow = true;
        listview.setAdapter(new MyAdapter());

    }

    /**
     * true是否显示ListView,但Gridview隐藏
     * flase 是GridView显示，但ListView隐藏
     */
    private boolean isListViewShow = true;

    public void switchListAndGrid(ImageButton ib_switche_mode)
    {
        if (isListViewShow)
        {
            isListViewShow = false;
            //gridView显示
            gridview.setVisibility(View.VISIBLE);
            gridview.setAdapter(new MyAdapter());
            //listView隐藏
            listview.setVisibility(View.GONE);

            //按钮设置listView的状态
            ib_switche_mode.setImageResource(R.drawable.icon_pic_list_type);
        }
        else
        {
            isListViewShow = true;
            //listVeiw显示
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new MyAdapter());
            //girdVeiw隐藏
            gridview.setVisibility(View.GONE);
            //按钮设置gridview的状态
            ib_switche_mode.setImageResource(R.drawable.icon_pic_grid_type);
        }

    }


    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return news.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                convertView = View.inflate(mActivity, R.layout.photos_item, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_photos_icon = (ImageView) convertView.findViewById(R.id
                        .iv_photos_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }
            else
            {

                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.iv_photos_icon.setBackgroundResource(R.drawable.pic_item_list_default);

            PhotosMenuDetailBean.DataEntity.NewsEntity newsEntity = news.get(position);
            viewHolder.tv_title.setText(newsEntity.getTitle());

            //xUtils3-->Volley请求ListView中的图片
            //            x.image().bind( viewHolder.iv_photos_icon,newsEntity.getListimage());
            System.out.println("newsEntity.getListimage()==" + newsEntity.getListimage());

            //            loaderImager(viewHolder, newsEntity.getListimage());

            viewHolder.iv_photos_icon.setTag(position);

//            Bitmap bitmap = bitmapCacheUtils.getBitmap(Url.BASE_URL + newsEntity.getListimage(),
//                    position);
//            if (bitmap != null)
//            {//内存的图片，或者本地的图片
//                viewHolder.iv_photos_icon.setImageBitmap(bitmap);
//            }


            return convertView;
        }
    }




    static class ViewHolder
    {
        ImageView iv_photos_icon;
        TextView tv_title;
    }

    private PhotosMenuDetailBean parsedJson(String json)
    {
        return new Gson().fromJson(json, PhotosMenuDetailBean.class);
    }
}

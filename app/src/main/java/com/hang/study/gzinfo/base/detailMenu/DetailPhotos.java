package com.hang.study.gzinfo.base.detailMenu;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.bean.PhotoBean;
import com.hang.study.gzinfo.utils.Global;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by hang on 16/7/18.
 */
public class DetailPhotos extends BaseDetailMenu {
    public ImageView icon_list;
    public ListView lv_list;
    public GridView grid;
    public ArrayList<PhotoBean.PhotoNewsData> photoNews;
    public MyAdapter myAdapter;
    public DetailPhotos(Activity activity, ImageView grid) {
        super(activity);
        this.icon_list=grid;

    }
    boolean isList=true;
    @Override
    public View initView() {
       /* TextView text=new TextView(mActivity);
        text.setText("组播详情页");
        text.setGravity(Gravity.CENTER); */
        View view=View.inflate(mActivity, R.layout.photos_layout,null);
        lv_list= (ListView) view.findViewById(R.id.lv_list);
        grid= (GridView) view.findViewById(R.id.grid);
        return view;
    }

    @Override
    public void initData() {
        icon_list.setVisibility(View.VISIBLE);

        icon_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isList) {
                    isList=false;
                    icon_list.setImageResource(R.drawable.icon_pic_list_type);
                    lv_list.setVisibility(View.GONE);
                    grid.setVisibility(View.VISIBLE);
                }else {
                    isList=true;
                    icon_list.setImageResource(R.drawable.icon_pic_grid_type);
                    lv_list.setVisibility(View.VISIBLE);
                    grid.setVisibility(View.GONE);
                }
            }
        });
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Global.photoUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processData(String result) {
        Gson gson=new Gson();
        PhotoBean photoBean=gson.fromJson(result, PhotoBean.class);
        photoNews=photoBean.data.news;
        myAdapter=new MyAdapter();
        lv_list.setAdapter(myAdapter);
        grid.setAdapter(myAdapter);

    }

    public class MyAdapter extends BaseAdapter {
        public BitmapUtils bitmapUtils;
        MyAdapter() {
            bitmapUtils=new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);

        }
        @Override
        public int getCount() {
            return photoNews.size();
        }

        @Override
        public PhotoBean.PhotoNewsData getItem(int position) {
            return photoNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=new ViewHolder();
            if(convertView==null) {
                convertView=View.inflate(mActivity,R.layout.photo_item,null);
                holder.photo= (ImageView) convertView.findViewById(R.id.photo);
                holder.text= (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.text.setText(photoNews.get(position).title);
            bitmapUtils.display(holder.photo,photoNews.get(position).listimage);
            return convertView;
        }

        public class ViewHolder{
            public ImageView photo;
            public TextView text;
        }
    }
}

package com.hang.study.gzinfo.base.detailMenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hang.study.gzinfo.R;
import com.hang.study.gzinfo.activity.NewsDetailActivity;
import com.hang.study.gzinfo.base.MyListView;
import com.hang.study.gzinfo.bean.NewsMenu;
import com.hang.study.gzinfo.bean.NewsTabBean;
import com.hang.study.gzinfo.utils.Global;
import com.hang.study.gzinfo.utils.SharePrefUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by hang on 16/7/20.
 */
public class TabNewsPager extends BaseDetailMenu {
    public NewsMenu.children children;
    public ViewPager viewPager;
    public NewsTabBean newsTabBean;
    public ArrayList<NewsTabBean.Topicnews> topics;
    public  BitmapUtils bitmapUtils;
    public TextView title;
    public CirclePageIndicator circlePageIndicator;
    public MyListView newsList;
    public ArrayList<NewsTabBean.NewsData> news;
    public listAdapter lAdapter;
    public String moreUrl;

    public TabNewsPager(Activity activity, NewsMenu.children children) {
        super(activity);
        this.children=children;
        bitmapUtils=new BitmapUtils(mActivity);
    }

    @Override
    public View initView() {
        View head=View.inflate(mActivity, R.layout.listview_head,null);
        viewPager= (ViewPager) head.findViewById(R.id.topnews);
        title= (TextView) head.findViewById(R.id.toptitle);
        circlePageIndicator= (CirclePageIndicator) head.findViewById(R.id.circle_point);
        View view=View.inflate(mActivity,R.layout.tab_news_pager,null);
        newsList= (MyListView) view.findViewById(R.id.newslist);
        newsList.addHeaderView(head);
        newsList.setOnReflashListener(new MyListView.OnReflashListerner() {
            @Override
            public void onReflash() {
                getDataFromServer();
            }

            @Override
            public void onLoad() {
                 if(moreUrl!=null) {
                     getMoreDataFromServer();
                 }else {
                     Toast.makeText(mActivity,"没有更多数据了",Toast.LENGTH_SHORT).show();
                     newsList.refleshComplete(true);
                 }
            }
        });
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Global.servername + children.url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                processData(responseInfo.result,false);
                newsList.refleshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                newsList.refleshComplete(false);
            }
        });
    }

    private void getMoreDataFromServer() {
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, moreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                processData(responseInfo.result,true);
                newsList.refleshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                newsList.refleshComplete(false);
            }
        });
    }

    private void processData(String result,boolean isMore) {

            Gson gson = new Gson();
            newsTabBean = gson.fromJson(result, NewsTabBean.class);
            topics = newsTabBean.data.topnews;
            if (!TextUtils.isEmpty(newsTabBean.data.more)) {
                moreUrl = Global.servername + newsTabBean.data.more;
            } else {
                moreUrl = null;
            }
        if(!isMore) {
            this.news = newsTabBean.data.news;
            viewPager.setAdapter(new MyAdapter());
            circlePageIndicator.setViewPager(viewPager);
            circlePageIndicator.setSnap(true);
            viewPager.setCurrentItem(0);
            title.setText(topics.get(0).title);
            lAdapter = new listAdapter();

            newsList.setAdapter(lAdapter);
            newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int click=position-newsList.getHeaderViewsCount();
                    System.out.println("点击了第"+click);
                    String read_ids= SharePrefUtil.getString(mActivity,"read_ids","");
                    if(!read_ids.contains(news.get(click).id+"")) {
                        SharePrefUtil.setString(mActivity,"read_ids",news.get(click).id+"");
                        TextView textView= (TextView) view.findViewById(R.id.newstitle);
                        TextView time= (TextView) view.findViewById(R.id.date);
                        System.out.println("===>"+time.getCurrentTextColor());
                        textView.setTextColor(time.getCurrentTextColor());
                    }
                    Intent intent=new Intent(mActivity, NewsDetailActivity.class);
                    intent.putExtra("url",news.get(position).url);
                    mActivity.startActivity(intent);
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    title.setText(topics.get(position).title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
            circlePageIndicator.setCurrentItem(0);
        }else {
            this.news.addAll(newsTabBean.data.news);
            lAdapter.notifyDataSetChanged();
        }
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return topics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            bitmapUtils.display(imageView, topics.get(position).topimage);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }
    class listAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return news.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=new ViewHolder();
            if(convertView==null) {
                convertView=View.inflate(mActivity,R.layout.news_list_item,null);
                holder.image= (ImageView) convertView.findViewById(R.id.newsimage);
                holder.title= (TextView) convertView.findViewById(R.id.newstitle);
                holder.date= (TextView) convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            String read_ids=SharePrefUtil.getString(mActivity,"read_ids","") ;
            if(read_ids.contains(news.get(position).id+"")) {
                holder.title.setTextColor(mActivity.getResources().getColor(R.color.colorGray));
            }else  {
                holder.title.setTextColor(mActivity.getResources().getColor(R.color.colorBlack));
            }
            bitmapUtils.display(holder.image,news.get(position).listimage);
            holder.title.setText(news.get(position).title);
            holder.date.setText(news.get(position).pubdate);
            return convertView;
        }
        class ViewHolder {
            public ImageView image;
            public TextView title;
            public TextView date;
        }
    }


}

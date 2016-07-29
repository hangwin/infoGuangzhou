package com.hang.study.gzinfo.bean;

import java.util.ArrayList;

/**
 * Created by hang on 16/7/21.
 */
public class NewsTabBean {
    public NewsTabData data;
    public class NewsTabData {
        public String more;
        public ArrayList<NewsData> news;
        public ArrayList<Topicnews> topnews;
    }

    public class NewsData {
        public int id;
        public String commentlist;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }

   public class Topicnews{
        public int id;
        public String pubdate;
        public String title;
        public String type;
        public String url;
        public String topimage;
    }
}

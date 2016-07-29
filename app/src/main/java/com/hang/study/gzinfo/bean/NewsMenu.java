package com.hang.study.gzinfo.bean;

import java.util.ArrayList;

/**
 * Created by hang on 16/7/17.
 */
public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<leftTab> data;

    public class leftTab {
        public int id;
        public String title;
        public int type;
        public ArrayList<children> children;
    }

    public class children {
        public int id;
        public String title;
        public int type;
        public String url;
    }
}

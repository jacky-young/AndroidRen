package com.jack.androidren.entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 4/12/16.
 */
public class NewsListObject {
    private List<NewsItem> list = new ArrayList<NewsItem>();
    private String type;
    private Integer page;

    public List<NewsItem> getList() {
        return list;
    }

    public void setList(List<NewsItem> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

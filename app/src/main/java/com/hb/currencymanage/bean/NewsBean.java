package com.hb.currencymanage.bean;

/**
 * Created by 汪彬 on 2018/4/18.
 */

public class NewsBean
{
    private String title;
    
    private String publisher;
    
    private String imgUrl;
    
    private String publishTime;
    
    public NewsBean(String title, String publisher, String imgUrl,
            String publishTime)
    {
        this.title = title;
        this.publisher = publisher;
        this.imgUrl = imgUrl;
        this.publishTime = publishTime;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getPublisher()
    {
        return publisher;
    }
    
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }
    
    public String getImgUrl()
    {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }
    
    public String getPublishTime()
    {
        return publishTime;
    }
    
    public void setPublishTime(String publishTime)
    {
        this.publishTime = publishTime;
    }
}

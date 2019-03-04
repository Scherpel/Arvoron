package com.arvoron.scherpel.arvoron;


import java.util.Date;

public class BlogPost{
    public String user_id, image_url, desc, tree_name, tree_family, tree_location, image_thumb;
    public Date timestamp;


    public BlogPost(){

    }
    public BlogPost(String user_id, String image_url, String desc, String tree_name, String tree_family, String tree_location, String image_thumb, Date timestamp) {
        this.user_id = user_id;
        this.image_url = image_url;
        this.desc = desc;
        this.tree_name = tree_name;
        this.tree_family = tree_family;
        this.tree_location = tree_location;
        this.image_thumb = image_thumb;
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTree_name() {
        return tree_name;
    }

    public void setTree_name(String tree_name) {
        this.tree_name = tree_name;
    }

    public String getTree_family() {
        return tree_family;
    }

    public void setTree_family(String tree_family) {
        this.tree_family = tree_family;
    }

    public String getTree_location() {
        return tree_location;
    }

    public void setTree_location(String tree_location) {
        this.tree_location = tree_location;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}

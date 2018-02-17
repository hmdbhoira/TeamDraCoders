package com.example.user.myapp1;

/**
 * Created by haveryahad on 1/16/2018.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Upload {

    public String name;
    public String url;
    public String location;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public Upload(String name, String url, String location) {

        this.name = name;
        this.url= url;
        this.location = location;
    }

    public  String getLocation(){
        return location;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}

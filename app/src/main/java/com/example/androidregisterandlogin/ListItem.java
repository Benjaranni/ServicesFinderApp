package com.example.androidregisterandlogin;

import com.google.gson.annotations.SerializedName;

public class ListItem {

    @SerializedName("name")
    private String head;

    @SerializedName("lastname")
    private String lname;

    @SerializedName("surname")
    private String sname;

    @SerializedName("phonenumber")
    private String desc;

    @SerializedName("expertise")
    private String exp;

    @SerializedName("location")
    private String locate;

    @SerializedName("photo")
    private String imageUrl;

    public String getHead() {
        return head;
    }

    public String getLname() {
        return lname;
    }

    public String getSname() {
        return sname;
    }

    public String getDesc() {
        return desc;
    }

    public String getExp() {
        return exp;
    }

    public String getLocate() {
        return locate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ListItem(String head, String desc, String imageUrl, String lname, String sname, String exp, String locate){

        this.head = head;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.lname = lname;
        this.sname = sname;
        this.exp = exp;
        this.locate = locate;

    }
}
/*
    //private  String head;
   // private String desc;
  //  private  String imageUrl;
  //  private String lname;
   // private String sname;
  //  private String exp;
  //  private String locate;


    public String getImageUrl() {
       return imageUrl;
    }

    public String getLname() {
        return lname;
    }

    //public String getSname() {
      //  return sname;
   // }



    public String getExp() {
        return exp;
    }

    public String getLocate() {
        return locate;
    }

    public ListItem(String head, String desc, String imageUrl, String lname, String sname, String exp, String locate){

        this.head = head;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.lname = lname;
        this.sname = sname;
        this.exp = exp;
        this.locate = locate;

    }

   // public String getHead() {
      //  return head;
   // }

   // public String getDesc() {
      //  return desc;
   // }

 */


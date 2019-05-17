package com.zm.mds.mds_support.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moska on 31.10.2017.
 */

public class Card {

    @SerializedName("shareItem")
    @Expose
    private ShareItem shareItem;
    @SerializedName("userFullName")
    @Expose
    private String userFullName;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;

//    @SerializedName("result")
//    @Expose
//    private Boolean result;

    public ShareItem getShareItem() {
        return shareItem;
    }

    public void setShareItem(ShareItem shareItem) {
        this.shareItem = shareItem;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //    public Boolean getResult() {
//        return result;
//    }
//
//    public void setResult(Boolean result) {
//        this.result = result;
//    }

}
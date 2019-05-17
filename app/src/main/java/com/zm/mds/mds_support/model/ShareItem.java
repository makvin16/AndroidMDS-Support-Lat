package com.zm.mds.mds_support.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moska on 31.10.2017.
 */

public class ShareItem {

    @SerializedName("organizationId")
    @Expose
    private Integer organizationId;
    @SerializedName("organizationName")
    @Expose
    private String organizationName;
    @SerializedName("objId")
    @Expose
    private Integer objId;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("customBarcode")
    @Expose
    private Object customBarcode;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("dateInNew")
    @Expose
    private String dateInNew;
    @SerializedName("objDescription")
    @Expose
    private String objDescription;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("countLimit")
    @Expose
    private Integer countLimit;
    @SerializedName("downloadCount")
    @Expose
    private Integer downloadCount;
    @SerializedName("timeLimit")
    @Expose
    private String timeLimit;
    @SerializedName("detailsUrl")
    @Expose
    private String detailsUrl;
    @SerializedName("images")
    @Expose
    private ArrayList<Object> images = null;
    @SerializedName("uniqueCode")
    @Expose
    private Object uniqueCode;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Object getCustomBarcode() {
        return customBarcode;
    }

    public void setCustomBarcode(Object customBarcode) {
        this.customBarcode = customBarcode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDateInNew() {
        return dateInNew;
    }

    public void setDateInNew(String dateInNew) {
        this.dateInNew = dateInNew;
    }

    public String getObjDescription() {
        return objDescription;
    }

    public void setObjDescription(String objDescription) {
        this.objDescription = objDescription;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Integer getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public ArrayList<Object> getImages() {
        return images;
    }

    public void setImages(ArrayList<Object> images) {
        this.images = images;
    }

    public Object getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Object uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

}

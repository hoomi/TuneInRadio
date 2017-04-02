package com.hoomi.tuneinapi.categories.model;

import com.google.gson.annotations.SerializedName;

public class Genre extends BrowsableItem {
    @SerializedName("guide_id")
    private String guideId;

    public String getGuideId() {
        return guideId;
    }
}

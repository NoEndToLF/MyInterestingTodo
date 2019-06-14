package com.example.douyin.video.model;

public class DouyinVideoModel {
    private String userName;
    private String videoContent;
    private String videoUrl;
    private String imgUrl;
    private boolean isLike;

    public DouyinVideoModel(String userName,  String imgUrl,String videoUrl) {
        this.userName = userName;
        this.videoUrl = videoUrl;
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVideoContent() {
        return videoContent;
    }

    public void setVideoContent(String videoContent) {
        this.videoContent = videoContent;
    }


    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}

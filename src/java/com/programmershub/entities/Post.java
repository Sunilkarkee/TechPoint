package com.programmershub.entities;

import java.sql.Timestamp;

public class Post {
    private int pId;
    private String pTitle;
    private String pContent;
    private String pCode;
    private String pPic;
    private Timestamp pDate;
    private int catId;
    private int userId;

 
    public Post(String pTitle, String pContent, String pCode, String pPic,int catId, int userId) {
  
        this.pTitle = pTitle;
        this.pContent = pContent;
        this.pCode = pCode;
        this.pPic = pPic;
        
        this.catId = catId;
        this.userId = userId;
    }

    // Constructor with all fields
    public Post(int pId, String pTitle, String pContent, String pCode, String pPic, Timestamp pDate, int catId, int userId) {
        this.pId = pId;
        this.pTitle = pTitle;
        this.pContent = pContent;
        this.pCode = pCode;
        this.pPic = pPic;
        this.pDate = pDate;
        this.catId = catId;
        this.userId = userId;
    }

    // Getters and Setters
    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public String getPTitle() {
        return pTitle;
    }

    public void setPTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getPContent() {
        return pContent;
    }

    public void setPContent(String pContent) {
        this.pContent = pContent;
    }

    public String getPCode() {
        return pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public String getPPic() {
        return pPic;
    }

    public void setPPic(String pPic) {
        this.pPic = pPic;
    }

    public Timestamp getPDate() {
        return pDate;
    }

    public void setPDate(Timestamp pDate) {
        this.pDate = pDate;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pId=" + pId +
                ", pTitle='" + pTitle + '\'' +
                ", pContent='" + pContent + '\'' +
                ", pCode='" + pCode + '\'' +
                ", pPic='" + pPic + '\'' +
                ", pDate=" + pDate +
                ", catId=" + catId +
                ", userId=" + userId +
                '}';
    }
}
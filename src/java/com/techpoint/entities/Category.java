
package com.techpoint.entities;

import java.sql.Timestamp;


public class Category {
    private int cid;
    private String name;
    private String description;
    private String photo;
    private Timestamp regDate;

    public Category(int cid, String name, String description, String photo) {
        this.cid = cid;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "Category{" + "cid=" + cid + ", name=" + name + ", description=" + description + ", photo=" + photo + ", regDate=" + regDate + '}';
    }
    
    
}

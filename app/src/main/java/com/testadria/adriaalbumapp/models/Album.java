package com.testadria.adriaalbumapp.models;

/**
 * Created by OUSSAMA on 23/01/2018.
 */

public class Album {

    private String created_time;
    private String name;
    private String id;
    private String cover_photo;


    public Album(String created_time, String name, String id, String cover_photo) {
        this.created_time = created_time;
        this.name = name;
        this.id = id;
        this.cover_photo = cover_photo;
    }

    public Album() {
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Override
    public String toString() {
        return "Album{" +
                "created_time='" + created_time + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}

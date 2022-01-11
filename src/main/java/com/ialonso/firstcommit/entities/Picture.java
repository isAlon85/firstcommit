package com.ialonso.firstcommit.entities;

import javax.persistence.*;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private
    Long id;

    @Column
    private String url;

    @Column
    private String cloudinaryId;

    public Picture() {
    }

    public Picture(Long id, String url, String cloudinaryId) {
        this.id = id;
        this.url = url;
        this.cloudinaryId = cloudinaryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCloudinaryId() {
        return cloudinaryId;
    }

    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", cloudinaryId='" + cloudinaryId + '\'' +
                '}';
    }

}

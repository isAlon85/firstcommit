package com.ialonso.firstcommit.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Student {

    @Id
    @OrderBy("id ASC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column (nullable = false)
    @Size(max = 50)
    private String name;

    @Column (unique = true, nullable = false)
    @Size(max = 48)
    private String email;

    @Column (unique = true, nullable = false)
    @Size(max = 20)
    private String phone;

    @Column (nullable = false)
    @Size(max = 32)
    private String country;

    @Column (nullable = false)
    @Size(max = 48)
    private String location;

    @Column (nullable = false)
    private Boolean mobility;

    @Column (nullable = false)
    private Integer remote;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Picture picture;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Resume resume;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    @JoinTable(name = "STUDENT_TAGS",
            joinColumns = {
                    @JoinColumn(name = "STUDENT_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "TAG_ID") })

    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user;

    public Student() {
    }

    public Student(Long id, String name, String email, String phone, String country, String location, Boolean mobility, Integer remote, Picture picture, Resume resume, Set<Tag> tags, User user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.location = location;
        this.mobility = mobility;
        this.remote = remote;
        this.picture = picture;
        this.resume = resume;
        this.tags = tags;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isMobility() {
        return mobility;
    }

    public void setMobility(boolean mobility) {
        this.mobility = mobility;
    }

    public Integer getRemote() {
        return remote;
    }

    public void setRemote(int remote) {
        this.remote = remote;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", mobility=" + mobility +
                ", remote=" + remote +
                ", picture=" + picture +
                ", resume=" + resume +
                ", tags=" + tags +
                ", user=" + user +
                '}';
    }
}

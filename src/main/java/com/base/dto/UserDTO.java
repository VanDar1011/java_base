package com.base.dto;

import java.io.Serializable;

public class UserDTO {
    private int id;
    private String name;
    private ProfileDTO profile;

    public UserDTO() {
    }

    public UserDTO(int id, String name, ProfileDTO profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }



}

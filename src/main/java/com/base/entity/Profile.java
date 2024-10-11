package com.base.entity;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity(name = "profile")
public class Profile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "address", length = 50, nullable = false)
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", address='" + address + 
                '}';
    }
}

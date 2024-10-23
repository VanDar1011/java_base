package com.base.enums;

public enum Role {
    ADMIN(1), USER(2);
    public final int value;

    Role(int value) {
        this.value = value;
    }
}

package com.base.utils;

public enum ResponseStatus {
    OK("ok"),
    FAIL("fail");

    private final String status;

    // Constructor
    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

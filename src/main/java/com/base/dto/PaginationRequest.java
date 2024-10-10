package com.base.dto;

public class PaginationRequest {
    private int page;
    private int size;

    // Constructor
    public PaginationRequest() {}

    public PaginationRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
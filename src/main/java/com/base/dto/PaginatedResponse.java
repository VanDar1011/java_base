package com.base.dto;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> content;  // Danh sách đối tượng
    private long totalCount;  // Tổng số bản ghi

    // Constructor
    public PaginatedResponse(List<T> content, long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }

    // Getters and Setters
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
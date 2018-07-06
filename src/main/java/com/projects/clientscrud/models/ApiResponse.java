package com.projects.clientscrud.models;

import java.util.List;

public class ApiResponse {

    private String status;
    private String message;
    private List<ITypeListResponse> response;

    public ApiResponse(String status, String message, List<ITypeListResponse> response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ITypeListResponse> getResponse() {
        return response;
    }

    public void setResponse(List<ITypeListResponse> response) {
        this.response = response;
    }
}

package com.example.taskmanager.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    
    @JsonProperty("dueDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate DueDate;

    private Long projectId;

    public TaskResponseDto () {}

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }


    public LocalDate getDueDate() {
        return DueDate;
    }


    public void setDueDate(LocalDate dueDate) {
        DueDate = dueDate;
    }

    
}

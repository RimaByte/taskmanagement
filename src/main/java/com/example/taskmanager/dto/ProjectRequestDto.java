package com.example.taskmanager.dto;

public class ProjectRequestDto {

    private String name;
    private String description;

    public ProjectRequestDto(){}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
}

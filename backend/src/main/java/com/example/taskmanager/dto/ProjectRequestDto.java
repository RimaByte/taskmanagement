package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequestDto {

    // Projektname ist Pflicht
    @NotBlank(message = "Projektname darf nicht leer sein")
    @Size(max = 100, message = "Projektname darf maximal 100 Zeichen lang sein")
    private String name;

    // Beschreibung ist optional (kein @NotBlank) aber begrenzt auf 500 Zeichen
    @Size(max = 500, message = "Beschreibung darf maximal 500 Zeichen lang sein")
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

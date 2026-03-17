package com.example.taskmanager.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskRequestDto {

    // Titel ist Pflicht
    @NotBlank(message = "Titel darf nicht leer sein")
    @Size(max = 200, message = "Titel darf maximal 200 Zeichen lang sein")
    private String title;

    // Beschreibung ist optional
    @Size(max = 1000, message = "Beschreibung darf maximal 1000 Zeichen lang sein")
    private String description;

    // Status muss angegeben werden (OPEN, IN_PROGRESS, DONE)
    @NotBlank(message = "Status darf nicht leer sein")
    private String status;

    // Prioritaet muss angegeben werden (LOW, MEDIUM, HIGH)
    @NotBlank(message = "Prioritaet darf nicht leer sein")
    private String priority;

    // @FutureOrPresent: das Datum darf nicht in der Vergangenheit liegen
    // @NotNull weil @FutureOrPresent null-Werte ignoriert (null = keine Prüfung)
    @NotNull(message = "Faelligkeitsdatum darf nicht leer sein")
    @FutureOrPresent(message = "Faelligkeitsdatum darf nicht in der Vergangenheit liegen")
    private LocalDate DueDate;

    // projectId kommt aus dem URL-Pfad, nicht aus dem Body - daher keine Validierung noetig
    private Long projectId;
    

    

    public TaskRequestDto() {
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

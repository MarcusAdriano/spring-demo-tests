package com.marcus.demoapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    private Date completeAt;
    private Date createAt;
    private Date updateAt;
    private String description;
    private boolean isComplete;
    private long id;

    public Todo(Date completeAt, Date createAt, Date updateAt, String description, boolean isComplete) {
        this.completeAt = completeAt;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.description = description;
        this.isComplete = isComplete;
    }
}

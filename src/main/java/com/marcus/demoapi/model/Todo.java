package com.marcus.demoapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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

}

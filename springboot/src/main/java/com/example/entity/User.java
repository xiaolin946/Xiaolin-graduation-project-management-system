package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer id;
    private String status;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp register_date;
    private String name;
    private String sex;
    private Integer age;
}

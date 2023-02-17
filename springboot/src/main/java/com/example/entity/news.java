package com.example.entity;

import lombok.Data;

@Data
public class news {
    private Integer xid;
    private String xstatus;
    private String puser;
    private String guser;
    private String title;
    private String content;
    private String pdate;
}

package com.mywork.goohaetest.domain.admin;

import lombok.Data;

@Data
public class ManagerVO {

    private int managerSeq;
    private String id;
    private String password;
    private String name;
    private String joinDate;
    private String auth;
    private String status;

}

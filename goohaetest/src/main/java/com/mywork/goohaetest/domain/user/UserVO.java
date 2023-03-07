package com.kdt.goohae.domain.user;


import lombok.Data;

@Data
public class UserVO {

    private int userSeq;
    private String id;
    private String password;
    private String name;
    private int postNumber;
    private String address;
    private String phoneNumber;
    private String joinDate;
    private String status;
    private String auth;

}

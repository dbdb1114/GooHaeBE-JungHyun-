package com.mywork.goohaetest.domain.admin;

import lombok.Data;

@Data
public class PostBoardVO{

    private int postSeq;
    private String managerId;
    private String title;
    private String content;
    private String regDate;

}

package com.kdt.goohae.domain.user;


import lombok.Data;

@Data
public class QnaBoardVO {

    private int boardSeq;
    private String userId;
    private String title;
    private String content;
    private String boardPassword;

}

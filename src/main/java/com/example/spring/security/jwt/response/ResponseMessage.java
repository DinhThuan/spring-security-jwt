package com.example.spring.security.jwt.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String message;
    private String fileDownloadUri;
    private Object data;
    private String code = "0000";
}

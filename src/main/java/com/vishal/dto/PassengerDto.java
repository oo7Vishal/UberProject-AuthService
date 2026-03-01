package com.vishal.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDto {

    private String id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    private Date createdAt;

}

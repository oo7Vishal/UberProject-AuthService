package com.vishal.dto;

import com.vishal.models.Passenger;
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
    private String password; // encrypted password
    private String name;
    private String phoneNumber;

    private Date createdAt;

    public static PassengerDto from(Passenger p) {
        PassengerDto result = PassengerDto.builder()
                //.id(p.getId())
               // .createdAt(p.getCreatedAt())
                .email(p.getEmail())
                .name(p.getName())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .build();

        return result;
    }

}

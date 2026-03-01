package com.vishal.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerSignupRequestDto {

   private String email;
   private String password;
   private String name;
   private String phoneNumber;
}

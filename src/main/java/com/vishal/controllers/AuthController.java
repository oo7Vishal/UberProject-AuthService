package com.vishal.controllers;

import com.vishal.dto.PassengerDto;
import com.vishal.dto.PassengerSignupRequestDto;
import com.vishal.services.AuthService;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto signupRequestDto) {
        PassengerDto passengerDto = authService.signUpPassenger(signupRequestDto);

        return new ResponseEntity<>(passengerDto,HttpStatus.CREATED) ;
    }

}

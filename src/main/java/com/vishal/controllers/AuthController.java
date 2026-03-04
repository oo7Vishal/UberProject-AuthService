package com.vishal.controllers;

import com.vishal.dto.AuthRequestDto;
import com.vishal.dto.PassengerDto;
import com.vishal.dto.PassengerSignupRequestDto;
import com.vishal.services.AuthService;
import com.vishal.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtService jwtService;

            @Value("${cookie.expiry}")
            private int cookieExpiry;

    public AuthController(AuthService authService,JwtService jwtService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto signupRequestDto) {
        PassengerDto passengerDto = authService.signUpPassenger(signupRequestDto);

        return new ResponseEntity<>(passengerDto,HttpStatus.CREATED) ;
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<String> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getEmail(),
                            authRequestDto.getPassword()
                    )
            );

            if (authenticate.isAuthenticated()) {
                Map<String,Object> payLoad = new HashMap<>();
                payLoad.put("email",authRequestDto.getEmail());

                String jwtToken  = jwtService.generateToken(authRequestDto.getEmail());
                ResponseCookie cookie = ResponseCookie.from("JwtToken",jwtToken)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(cookieExpiry)
                        .build();

                response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
                return ResponseEntity.ok(jwtToken);
            }
        } catch (Exception e) {
            // Wrong credentials → 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Auth not successful");
    }

}

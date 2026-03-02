package com.vishal.services;

import com.vishal.dto.PassengerDto;
import com.vishal.dto.PassengerSignupRequestDto;
import com.vishal.models.Passenger;
import com.vishal.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public PassengerDto signUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto) {
        Passenger passenger = Passenger.builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword())) // TODO : encryptiion to be done
                .build();

        Passenger newPassanger = passengerRepository.save(passenger);
        return PassengerDto.from(newPassanger);
    }

}

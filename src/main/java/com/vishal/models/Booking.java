package com.vishal.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

import com.vishal.models.Driver;   // ← your own entity
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends BaseModal {

//    @OneToMany( mappedBy = "booking", cascade = {CascadeType.PERSIST} , fetch = FetchType.LAZY)
//    private List<Review> review;

    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;



    private Date startDate;
    private Date endDate;

    private Long totalDistance;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Passenger passenger;

}


package com.vishal.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver extends BaseModal {

    private String name;

    @Column(unique = true,nullable = false)
    private String licenseNumber;

    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER )
    private List<Booking> bookings = new ArrayList<>();
}


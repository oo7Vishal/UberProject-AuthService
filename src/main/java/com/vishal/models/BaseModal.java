package com.vishal.models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@MappedSuperclass
public abstract class BaseModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;

    @CreationTimestamp
    protected Date createdAt;

    @UpdateTimestamp
    protected Date updatedAt;





}

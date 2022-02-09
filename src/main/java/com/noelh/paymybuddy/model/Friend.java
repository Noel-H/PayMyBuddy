package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String friendLoginMail;
}

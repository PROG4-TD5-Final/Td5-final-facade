package com.example.prog4.repository.database2.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CnapsEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String cin;
    private String cnaps;
    private String image;
    private String address;
    private String lastName;
    private String firstName;
    private String personalEmail;
    private String professionalEmail;
    private String registrationNumber;
    private LocalDate birthdate;
    private LocalDate entranceDate;
    private LocalDate departureDate;
    private Integer childrenNumber;

    @Column(name = "end_to_end_id")
    private String endToEndId;
}

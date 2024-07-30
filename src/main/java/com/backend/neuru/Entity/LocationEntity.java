package com.backend.neuru.Entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location_name;
    private String latitude;
    private String longitude;
    private int category;
    private Boolean is_ad;

}

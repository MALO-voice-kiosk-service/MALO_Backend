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
    private int keyword; // keyword도 키워드에 번호 지정해두고, DB에는 int만 저장

}

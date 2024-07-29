package com.backend.neuru.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="walkway")
public class WalkwayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String walkway_title;
    private String walkway_description;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;


}

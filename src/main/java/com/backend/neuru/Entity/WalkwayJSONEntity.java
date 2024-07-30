package com.backend.neuru.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="walkway_json")
public class WalkwayJSONEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city_name;
    private String quality_description;
    private String width;
    private String inclination;
    private String texture;
    private String COT_CONTS_GEOM;
}

package com.backend.neuru.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String post_title;
    private String post_content;
    private int post_tag;
    private String location;
//    private String post_img;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


}

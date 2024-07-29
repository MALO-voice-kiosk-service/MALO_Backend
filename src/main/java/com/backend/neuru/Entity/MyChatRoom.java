package com.backend.neuru.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="my_chatroom")
public class MyChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exit_time")
    private LocalTime exit_time;

    @Column(name = "enter_time")
    private LocalTime enter_time;

    @Column(name = "is_read")
    private Boolean is_read;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;


}

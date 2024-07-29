package com.backend.neuru.DTO;

import com.backend.neuru.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthDTO {

    private String userid;
    private String name;
    private String nickname;
    private String password;

}

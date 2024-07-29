package com.backend.neuru.DTO;

import com.backend.neuru.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AuthDTO {

    private String userid;
    private String name;
    private String nickname;
    private String password;

    public UserEntity toEntity(String password, List<String> roles) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // password 암호화
        String encodedPassword = passwordEncoder.encode(password);

        return UserEntity.builder()
                .userid(userid)
                .nickname(nickname)
                .name(name)
                .password(encodedPassword)
                .build();

    }
}

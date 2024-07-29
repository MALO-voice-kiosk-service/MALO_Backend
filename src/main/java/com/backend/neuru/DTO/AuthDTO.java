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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SignupDTO{
        private String userid;
        private String name;
        private String nickname;
        private String password;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class LonginDTO{
        private String userid;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class logoutDTO{
        private Long userid;
    }

}

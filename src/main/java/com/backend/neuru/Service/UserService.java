package com.backend.neuru.Service;

import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.UserEntity;
import com.backend.neuru.Repository.UserRepository;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public ResponseDTO<?> signup(AuthDTO authDTO) {

        Optional<UserEntity> userEntity = userRepository.findByUserid(authDTO.getUserid());
        if (userEntity.isEmpty()) {
            log.info("현재 중복된 유저 없음");
            userRepository.save(toEntity(authDTO));
            log.info(authDTO.getUserid(), authDTO.getName());
            return ResponseDTO.success("회원가입 성공", authDTO.getUserid());
        }else {
            log.info(userEntity.get().getName() + " already exist");
            // 유저 닉네임이 중복일 경우 중복 에러 발생
            throw new CustomException(ErrorCode.DUPLICATE_USER_NAME);
        }
    }

    private UserEntity toEntity(AuthDTO authDTO) {

        return UserEntity.builder()
                .userid(authDTO.getUserid())
                .nickname(authDTO.getNickname())
                .name(authDTO.getName())
                .password(authDTO.getPassword())
                .build();

    }
}

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
    public ResponseDTO<?> signup(AuthDTO.SignupDTO signupDTO) {

        Optional<UserEntity> userEntity = userRepository.findByUserid(signupDTO.getUserid());
        if (userEntity.isEmpty()) {
            log.info("현재 중복된 유저 없음");
            userRepository.save(toEntity(signupDTO));
            log.info(signupDTO.getUserid(), signupDTO.getName());
            return ResponseDTO.success("회원가입 성공", signupDTO.getUserid());
        }else {
            log.info(userEntity.get().getName() + " already exist");
            // 유저 닉네임이 중복일 경우 중복 에러 발생
            throw new CustomException(ErrorCode.DUPLICATE_USER_NAME);
        }
    }

    private UserEntity toEntity(AuthDTO.SignupDTO signupDTO) {

        return UserEntity.builder()
                .userid(signupDTO.getUserid())
                .nickname(signupDTO.getNickname())
                .name(signupDTO.getName())
                .password(signupDTO.getPassword())
                .build();

    }

    @Transactional
    public ResponseDTO<?> login(AuthDTO.LonginDTO longinDTO){
        Optional<UserEntity> userEntity = userRepository.findByUserid(longinDTO.getUserid());
        if (userEntity.isEmpty()) {
            log.info("회원가입한 유저가 아님");
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }else {
            return ResponseDTO.success("로그인 성공", longinDTO.getUserid());
        }
    }

    @Transactional
    public ResponseDTO<?> logout(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            log.info("회원가입한 유저가 아님");
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }else {
            return ResponseDTO.success("로그아웃 성공", userEntity);
        }
    }

    @Transactional
    public ResponseDTO<?> updateUser(Long id, String nickname) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()) {
            userEntity.get().setNickname(nickname);
            userRepository.save(userEntity.get());
            return ResponseDTO.success("회원정보 수정 완료", userEntity.get());
        } else{
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Transactional
    public ResponseDTO<?> getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()) {
            return ResponseDTO.success("회원정보 조회 완료", userEntity.get());
        } else{
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

}

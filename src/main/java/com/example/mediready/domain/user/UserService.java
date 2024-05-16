package com.example.mediready.domain.user;

import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public String signupUser(PostUserSignupReq postUserSignupReq) {

        if (userRepository.existsByEmail(postUserSignupReq.getEmail())) {
            throw new BaseException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByNickname(postUserSignupReq.getNickname())) {
            throw new BaseException(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }

        User user = postUserSignupReq.toUserEntity();
        user.encryptPassword(bCryptPasswordEncoder);
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }
}

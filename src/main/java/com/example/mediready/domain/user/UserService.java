package com.example.mediready.domain.user;

import com.example.mediready.domain.pharmacist.Pharmacist;
import com.example.mediready.domain.pharmacist.PharmacistRepository;
import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.common.mail.EmailService;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.EmailErrorCode;
import com.example.mediready.global.config.exception.errorCode.UserErrorCode;
import com.example.mediready.global.config.redis.RedisService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PharmacistRepository pharmacistRepository;
    private final EmailService emailService;
    private final RedisService redisService;
    private final PasswordEncoder bCryptPasswordEncoder;

    public String signupUser(PostUserSignupReq postUserSignupReq) {

        if (userRepository.existsByEmail(postUserSignupReq.getEmail())) {
            throw new BaseException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
        if (!"true".equals(redisService.getData("emailVerified:" + postUserSignupReq.getEmail()))) {
            throw new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED);
        }

        if (userRepository.existsByNickname(postUserSignupReq.getNickname())) {
            throw new BaseException(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }

        User user = postUserSignupReq.toUserEntity();
        user.encryptPassword(bCryptPasswordEncoder);
        userRepository.save(user);
        return user.getNickname();
    }

    public String signupPharmacist(PostPharmacistSignupReq postPharmacistSignupReq) {

        if (userRepository.existsByEmail(postPharmacistSignupReq.getEmail())) {
            throw new BaseException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
        if (!"true".equals(
            redisService.getData("emailVerified:" + postPharmacistSignupReq.getEmail()))) {
            throw new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED);
        }

        if (userRepository.existsByNickname(postPharmacistSignupReq.getNickname())) {
            throw new BaseException(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }

        User user = postPharmacistSignupReq.toUserEntity();
        Pharmacist pharmacist = postPharmacistSignupReq.toPharmacistEntity(user);
        user.encryptPassword(bCryptPasswordEncoder);
        userRepository.save(user);
        pharmacistRepository.save(pharmacist);

        return user.getNickname();
    }
}

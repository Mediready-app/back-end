package com.example.mediready.domain.user;

import com.example.mediready.domain.pharmacist.Pharmacist;
import com.example.mediready.domain.pharmacist.PharmacistRepository;
import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.S3.S3Service;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.EmailErrorCode;
import com.example.mediready.global.config.exception.errorCode.UserErrorCode;
import com.example.mediready.global.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PharmacistRepository pharmacistRepository;
    private final S3Service s3Service;
    private final RedisService redisService;
    private final PasswordEncoder bCryptPasswordEncoder;

    public String signupUser(MultipartFile imgFile, PostUserSignupReq postUserSignupReq) {
        validateSignupRequest(postUserSignupReq.getEmail(), postUserSignupReq.getNickname());

        User user = postUserSignupReq.toUserEntity();
        user.encryptPassword(bCryptPasswordEncoder);
        user.updateProfileImgUrl(uploadProfileImage(imgFile));

        userRepository.save(user);
        return user.getNickname();
    }

    @Transactional
    public String signupPharmacist(MultipartFile imgFile, MultipartFile licenseFile,
        PostPharmacistSignupReq postPharmacistSignupReq) {
        validateSignupRequest(postPharmacistSignupReq.getEmail(),
            postPharmacistSignupReq.getNickname());

        User user = postPharmacistSignupReq.toUserEntity();
        Pharmacist pharmacist = postPharmacistSignupReq.toPharmacistEntity(user);
        user.encryptPassword(bCryptPasswordEncoder);
        user.updateProfileImgUrl(uploadProfileImage(imgFile));
        pharmacist.updateLicenseFileUrl(uploadLicenseFile(licenseFile));

        userRepository.save(user);
        pharmacistRepository.save(pharmacist);

        return user.getNickname();
    }

    private void validateSignupRequest(String email, String nickname) {
        if (!"true".equals(redisService.getData("emailVerified:" + email))) {
            throw new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED);
        }
        if (userRepository.existsByNickname(nickname)) {
            throw new BaseException(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }
    }

    private String uploadProfileImage(MultipartFile imgFile) {
        if (imgFile == null) {
            return "기본 이미지";
        }
        return s3Service.upload(imgFile);
    }

    private String uploadLicenseFile(MultipartFile licenseFile) {
        if (licenseFile == null) {
            throw new BaseException(UserErrorCode.PHARMACIST_LICENSE_FILE_IS_EMPTY);
        }
        return s3Service.upload(licenseFile);
    }
}

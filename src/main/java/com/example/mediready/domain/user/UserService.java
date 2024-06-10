package com.example.mediready.domain.user;

import com.example.mediready.domain.folder.Folder;
import com.example.mediready.domain.folder.FolderRepository;
import com.example.mediready.domain.pharmacist.Pharmacist;
import com.example.mediready.domain.pharmacist.PharmacistRepository;
import com.example.mediready.domain.user.dto.GetPharmacistProfileInfoRes;
import com.example.mediready.domain.user.dto.GetUserProfileInfoRes;
import com.example.mediready.domain.user.dto.ModifyProfileReq;
import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostResetAccessTokenRes;
import com.example.mediready.domain.user.dto.PostUserLoginReq;
import com.example.mediready.domain.user.dto.PostUserLoginRes;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.S3.S3Service;
import com.example.mediready.global.config.auth.jwt.JwtTokenProvider;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.AuthErrorCode;
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
    private final FolderRepository folderRepository;
    private final S3Service s3Service;
    private final RedisService redisService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String signupUser(MultipartFile imgFile, PostUserSignupReq postUserSignupReq) {
        validateSignupRequest(postUserSignupReq.getEmail());

        User user = postUserSignupReq.toUserEntity();
        user.encryptPassword(bCryptPasswordEncoder);
        user.updateProfileImgUrl(uploadProfileImage(imgFile));

        Folder folder = new Folder();
        folder.createInitialFolder(user);

        userRepository.save(user);
        folderRepository.save(folder);

        return user.getNickname();
    }

    @Transactional
    public String signupPharmacist(MultipartFile imgFile, MultipartFile licenseFile,
        PostPharmacistSignupReq postPharmacistSignupReq) {
        validateSignupRequest(postPharmacistSignupReq.getEmail());

        User user = postPharmacistSignupReq.toUserEntity();
        Pharmacist pharmacist = postPharmacistSignupReq.toPharmacistEntity(user);
        user.encryptPassword(bCryptPasswordEncoder);
        user.updateProfileImgUrl(uploadProfileImage(imgFile));
        pharmacist.updateLicenseFileUrl(uploadLicenseFile(licenseFile));
        Folder folder = new Folder();
        folder.createInitialFolder(user);

        userRepository.save(user);
        pharmacistRepository.save(pharmacist);
        folderRepository.save(folder);

        return user.getNickname();
    }

    private void validateSignupRequest(String email) {
        if (!"true".equals(redisService.getData("emailVerifiedSignup:" + email))) {
            throw new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED);
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

    public PostUserLoginRes login(PostUserLoginReq postUserLoginReq) {
        User user = userRepository.findUserByEmailAndDeletedFalse(postUserLoginReq.getEmail())
            .orElse(null);
        if (user == null) {
            throw new BaseException(UserErrorCode.USER_NOT_FOUND);
        }

        if (!bCryptPasswordEncoder.matches(postUserLoginReq.getPassword(), user.getPassword())) {
            throw new BaseException(UserErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new PostUserLoginRes(user.getType(), accessToken, refreshToken);
    }

    public PostResetAccessTokenRes resetAccessToken(String refreshToken) {
        jwtTokenProvider.validateRefreshToken(refreshToken);

        Long userId = Long.parseLong(jwtTokenProvider.getUserIdByRefreshToken(refreshToken));
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || !refreshToken.equals(user.getRefreshToken())) {
            throw new BaseException(AuthErrorCode.INVALID_JWT);
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        return new PostResetAccessTokenRes(accessToken);
    }

    @Transactional
    public void logout(User user, String token) {
        user.deleteRefreshToken();
        userRepository.save(user);

        long expirationTime =
            jwtTokenProvider.getExpirationTime(token) - System.currentTimeMillis();
        redisService.addToBlacklist(token, expirationTime);
    }

    public void deleteUser(User user) {
        user.deleteRefreshToken();
        user.setDeleted(true);
        userRepository.save(user);
    }

    public Object getProfileInfo(User user) {
        if (user.getType() == UserRole.USER) {
            return GetUserProfileInfoRes.builder()
                .type(user.getType())
                .nickname(user.getNickname())
                .info(user.getInfo())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
        } else {
            Pharmacist pharmacist = pharmacistRepository.findByUser(user);
            return GetPharmacistProfileInfoRes.builder()
                .type(user.getType())
                .nickname(user.getNickname())
                .info(user.getInfo())
                .profileImgUrl(user.getProfileImgUrl())
                .location(pharmacist.getLocation())
                .mannerScore(pharmacist.getMannerScore())
                .reviewCnt(pharmacist.getReviewCnt())
                .likeCnt(pharmacist.getLikeCnt())
                .build();
        }
    }

    public void modifyProfile(User user, MultipartFile imgFile, String nickname) {
        user.setProfileImgUrl(uploadProfileImage(imgFile));
        user.setNickname(nickname);
        userRepository.save(user);
    }

    public void modifyInfo(User user, String info) {
        user.setInfo(info);
        userRepository.save(user);
    }

    public void modifyPassword(String email, String password) {
        if (!"true".equals(redisService.getData("emailVerifiedPassword:" + email))) {
            throw new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED);
        }
        User user = userRepository.findUserByEmailAndDeletedFalse(email)
            .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
        user.setPassword(password);
        user.encryptPassword(bCryptPasswordEncoder);
        userRepository.save(user);
    }

    public Boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}

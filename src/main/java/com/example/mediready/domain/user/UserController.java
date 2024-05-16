package com.example.mediready.domain.user;

import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/singup-user")
    public BaseResponse<String> singupUser(@RequestBody PostUserSignupReq postUserSignupReq) {
        return new BaseResponse<>(userService.singupUser(postUserSignupReq));
    }
}

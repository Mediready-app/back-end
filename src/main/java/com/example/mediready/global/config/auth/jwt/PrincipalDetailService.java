package com.example.mediready.global.config.auth.jwt;

import com.example.mediready.domain.user.User;
import com.example.mediready.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername : " + username);
        User user = userRepository.findUserByIdAndDeletedFalse(Long.parseLong(username))
            .orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        System.out.println("After loadUserByUsername : "+ user.getId());
        return new PrincipalDetails(user);
    }
}
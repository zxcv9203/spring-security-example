package com.example.bankspringsecurity.config.auth;

import com.example.bankspringsecurity.domain.user.User;
import com.example.bankspringsecurity.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티로 로그인이 될 때, 시큐리티가 loadUserByUsername() 실행해서 username을 체크
    // 없으면 오류
    // 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인 된 세션이 만들어짐
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalAuthenticationServiceException("인증 실패"));
        return new LoginUser(user);
    }
}

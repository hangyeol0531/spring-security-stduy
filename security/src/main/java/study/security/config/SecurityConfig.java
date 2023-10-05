package study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // spring security 필터가 스프링 필터체인에 등록
public class SecurityConfig {

    // 해당 메서드의 리턴되는 오브젝트를 Ioc로 등록해줌
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(authorize ->
                authorize
                    .requestMatchers("/user/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().permitAll() // 다른 요청들은 전부 허용
            ).formLogin()
            .loginPage("/loginForm");
        return http.build();
    }
}

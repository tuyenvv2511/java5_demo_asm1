package org.example.demo_j5_asm1.config;

import org.example.demo_j5_asm1.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()
                // Chỉ cho phép xem danh sách sản phẩm và chi tiết sản phẩm mà không cần đăng nhập
                .requestMatchers("/products", "/products/").permitAll()
                .requestMatchers("/products/ai-suggestion").permitAll()
                // Chỉ admin và seller mới có thể tạo, sửa, xóa sản phẩm
                .requestMatchers("/products/create", "/products/*/edit", "/products/*/delete").hasAnyRole("ADMIN", "SELLER")
                .requestMatchers("/products/**").permitAll() // Các endpoint khác như xem chi tiết
                .requestMatchers("/orders/buy/**").hasRole("BUYER")
                .requestMatchers("/orders/*/status").hasRole("SELLER")
                .requestMatchers("/orders/*/cancel").hasRole("BUYER")
                .requestMatchers("/orders/*/complete").hasRole("BUYER")
                .requestMatchers("/orders").authenticated()
                .requestMatchers("/sales/**").hasRole("SELLER")
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/categories/**").hasRole("ADMIN")
                .requestMatchers("/brands/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}

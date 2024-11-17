package ru.aidoc.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> {
            // Для примера используем InMemoryUserDetailsManager, можете заменить на реальную логику
            UserDetails user = User.withUsername("user")
                    .password("{noop}password") // {noop} указывает, что пароль не зашифрован, используйте шифрование в продакшене
                    .roles("USER")
                    .build();
            return Mono.just(user);
        };
    }
}
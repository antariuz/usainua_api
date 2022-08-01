package avada.media.usainua_api.config;

import avada.media.usainua_api.filter.CustomAuthenticationFilter;
import avada.media.usainua_api.filter.CustomAuthorizationFilter;
import avada.media.usainua_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final AppConst appConst;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class).build();
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, userService, appConst);
        customAuthenticationFilter.setFilterProcessesUrl("/auth/signin");
        http
                .csrf().disable()
                .cors().disable()
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests((authz) -> authz
                                .antMatchers("/auth/**").permitAll()
                                .antMatchers(appConst.getSWAGGER_PATHS_WHITELIST()).permitAll()
                                .antMatchers("/**").hasAuthority("ROLE_USER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new CustomAuthorizationFilter(appConst), UsernamePasswordAuthenticationFilter.class)
                .addFilter(customAuthenticationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}

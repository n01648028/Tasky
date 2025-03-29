package com.humber.Tasky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //used to control DaoAuthenticationProvider's UserDetailsService
    private final UserDetailsService myUserDetailsService;
    //filters the links that the user can access to it can change depending on if USER or ADMIN
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.myUserDetailsService = userDetailsService;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests( (authorize) -> authorize
                        //the list of all links
                        //permits for any user under /Tasky/home
                        .requestMatchers("/Tasky/home").permitAll()
                        //permits for any link under /Tasky/login/
                        .requestMatchers("/Tasky/login/**").permitAll()
                        //permits for any link under /register/
                        .requestMatchers("/register/**").permitAll()
                        //show an error page under /error
                        .requestMatchers("/error").permitAll()
                        //permits for any link under /Tasky/logout/
                        .requestMatchers("/Tasky/logout").permitAll()
                        //permits only if the user's role is USER or ADMIN for any link under /Tasky/menu/
                        .requestMatchers("/Tasky/menu/**").hasAnyRole("USER", "ADMIN")
                        //permits only if the user's role is ADMIN for any link under /Tasky/admin/
                        .requestMatchers("/Tasky/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(httpSecurityFormLoginConfigurer -> {
                    //declares /login as login page and permits all users
                    httpSecurityFormLoginConfigurer.loginPage("/login").permitAll();
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    //allows the logout page
                    httpSecurityLogoutConfigurer.permitAll();
                });
        return http.build();
    }
    //in memory creation of users
    @Bean
    public UserDetailsService userDetailsService() {
        //creates the details for the admin user with ADMIN role
        UserDetails user1 = User.withUsername("templates/admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        //creates the details for the admin user with USER role
        UserDetails user2 = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        //uses the details of the two users as described above to create the users
        return new InMemoryUserDetailsManager(user1, user2);
    }
    //setups the AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }
    @Bean
    public WebSecurityCustomizer ignoreResources() {
        //disables webSecurity for all links under /css/ and /h2-console/
        return (webSecurity) -> webSecurity.ignoring().requestMatchers("/css/**", "/h2-console/**");
    }
    //creates the passwordEncoder for the class
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

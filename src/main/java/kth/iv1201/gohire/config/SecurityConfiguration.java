package kth.iv1201.gohire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

/**
 * Configuration for Spring security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /**
     * Defines api URLs accessible to all users, logged in or not.
     */
    final String[] whitelist = { "/api/login", "/api/who", "/api/createApplicant"};

    /**
     * Configures security filters, which URLs are open and authorization only
     * @param http needed to configure websecurity
     * @return security filter chain to use for securing requests.
     * @throws Exception if SecurityFilterChain creation fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers(whitelist).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf // TODO unsafe
                        .ignoringRequestMatchers("/**") )
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(new HttpSessionSecurityContextRepository())
                )
                .logout(logout -> logout
                        .logoutUrl("/test/logout").permitAll()
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    /**
     * Creates an <code>AuthenticationManager</code> which manages the authentication of a user
     * @param userDetailsService <code>UserDetailsService</code> for specifying how to access users
     * @return the <code>AuthenticationManager</code>
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        return new ProviderManager(authenticationProvider);
    }


}
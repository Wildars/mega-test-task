package kg.project.megatest.config;

import kg.project.megatest.config.jwt.AuthEntryPointJwt;
import kg.project.megatest.config.jwt.AuthTokenFilter;
import kg.project.megatest.config.jwt.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(

                "http://localhost:8081"

        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTIONS", "PUT", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept", "Access-Control-Request-Method","Access-Control-Allow-Origin", "Access-Control-Request-Headers", "Accept-Language",
                "Authorization","multipart/form-data", "Content-Type", "Request-Name", "Request-Surname", "Origin", "X-Request-AppVersion",
                "X-Request-OsVersion", "X-Request-Device", "X-Requested-With","strict-origin","no-referrer-when-downgrade", "strict-origin-when-cross-origin","origin-when-cross-origin"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/api/v1/checkCertificate/**"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/auth/**", "/swagger-ui/", "/swagger-ui/**", "/swagger-ui.html",
                        "/swagger-resources/**", "/v2/api-docs", "/webjars/**","/api/v1/auth/**","/api/v1/roles/**","/api/v1/external/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .frameOptions().sameOrigin();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }

}

package aubay.lu.projetrh.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class ApplicationWebSecurityConfig extends WebSecurityConfigurerAdapter {

    final SecurityUserDetailsService securityUserDetailsService;

    public ApplicationWebSecurityConfig(SecurityUserDetailsService securityUserDetailsService){
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(httpServletRequest -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.applyPermitDefaultValues();
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                    corsConfiguration.setAllowedHeaders(
                            Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                                    "Accept", "Authorization","Access-Control-Allow-Origin"));
                    return corsConfiguration;
                })

                .and()
                .csrf().disable().httpBasic().and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("RECRUTEUR","ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN","RECRUTEUR", "CANDIDAT")
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(false).permitAll();
    }
}

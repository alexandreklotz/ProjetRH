package aubay.lu.projetrh.security;

import aubay.lu.projetrh.security.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class ApplicationWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private SessionFilter sessionFilter;


    public ApplicationWebSecurityConfig(SecurityUserDetailsService securityUserDetailsService){
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http.exceptionHandling().authenticationEntryPoint(
                (request, response, ex) -> response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage())
        ).and();

        http.authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("RECRUTEUR","ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN","RECRUTEUR", "CANDIDAT")
                .antMatchers("/basicauth", "/logintest", "/login", "/resetpassword").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .and()
                .logout().logoutUrl("/logout").invalidateHttpSession(true)
                .clearAuthentication(false).permitAll();


        http.addFilterBefore(
                sessionFilter,
                ChannelProcessingFilter.class
        );
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)

                .csrf().disable().httpBasic().and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("RECRUTEUR","ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN","RECRUTEUR", "CANDIDAT")
                .antMatchers("/basicauth", "/logintest", "/login", "/resetpassword").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .and()
                .logout().logoutUrl("/logout").invalidateHttpSession(true) //auparavant "/login"
                .clearAuthentication(false).permitAll();
    }*/
}

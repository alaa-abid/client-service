package com.example.client.clientservice.Config.security;
import com.example.client.clientservice.Filter.JwtFilter;
import com.example.client.clientservice.Service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder passwordEncoder;
    private final ClientServiceImpl clientService;

    private final String APP_ROOT = "/api_client";

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(ClientServiceImpl clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http    .authorizeRequests()
                .antMatchers(APP_ROOT+"/client").permitAll()
                .antMatchers(APP_ROOT+"/client/{id}/{amount}").permitAll()
                .antMatchers(APP_ROOT+"/clients").permitAll()
                .antMatchers(APP_ROOT+"/beneficiaires/agent/{id}").permitAll()
                .antMatchers(APP_ROOT+"/ph/{phoneNumber}").permitAll()
                .antMatchers(APP_ROOT+"/nc/{numerocompte}").permitAll()
                .antMatchers(APP_ROOT+"/cin/{cin}").permitAll()
                .antMatchers(APP_ROOT + "/login").permitAll();


    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        //auth.userDetailsService(agentService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(clientService);

        return provider;
}
}
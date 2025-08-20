package com.hogar;

import com.paypal.base.rest.APIContext;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Abre primero el login
        registry.addViewController("/").setViewName("redirect:/login");
        registry.addViewController("/Menu").setViewName("hogar/Menu");
        registry.addViewController("/Servicios").setViewName("hogar/Servicios");
        registry.addViewController("/Asistencia").setViewName("hogar/Asistencia");
        registry.addViewController("/Galeria").setViewName("hogar/Galeria");
        registry.addViewController("/Preguntas").setViewName("hogar/Preguntas");
    }

    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/administracion/**").hasRole("ADMIN")
                .requestMatchers("/carrito/**", "/reservas/**", "/taller/**").authenticated()  // Ya usa singular
                .requestMatchers("/registro", "/login").permitAll()
                .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .usernameParameter("cedula")
                .permitAll()
                .defaultSuccessUrl("/Menu", true)  
                )
                .logout((logout) -> logout
                .permitAll()
                .logoutSuccessUrl("/login?logout")
                );
        return http.build();
    }

    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }
}
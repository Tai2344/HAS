package com.hogar;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("hogar/Registro"); // home
        registry.addViewController("/registro").setViewName("hogar/Registro");
        registry.addViewController("/Menu").setViewName("hogar/Menu");
        registry.addViewController("/Servicios").setViewName("hogar/Servicios");
        registry.addViewController("/Asistencia").setViewName("hogar/Asistencia");
        registry.addViewController("/Talleres").setViewName("hogar/Talleres");
        registry.addViewController("/Galeria").setViewName("hogar/Galeria");
        registry.addViewController("/ZonaPago").setViewName("hogar/ZonaPago");
        registry.addViewController("/Administracion").setViewName("hogar/Administracion");
        registry.addViewController("/Preguntas").setViewName("hogar/Preguntas");
        registry.addViewController("/login").setViewName("hogar/login");

    }

    @Bean
    public SpringResourceTemplateResolver templateResolver_0() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setOrder(0);
        resolver.setCheckExistence(true);
        return resolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                // Páginas públicas
                .requestMatchers("/", "/registro", "/Menu/**",
                        "/Servicios/**", "/Asistencia/**", "/Talleres/**",
                        "/Galeria/**", "/ZonaPago/**", "/Preguntas/**",
                        "/login/**",
                        "/js/**", "/webjars/**")
                .permitAll()
                // Solo administrador puede acceder a esta ruta
                .requestMatchers("/Administracion/**").hasRole("ADMIN")
                // Cualquier otra solicitud requiere estar autenticado
                .anyRequest().authenticated()
                )
                // Configuración de login
                .formLogin((form) -> form
                .loginPage("/login") 
                .defaultSuccessUrl("/Menu", true)
                .permitAll()
                )
                // Configuración de logout
                .logout((logout) -> logout
                .permitAll()
                );

        return http.build();
    }

    /* El siguiente método se utiliza para completar la clase no es 
    realmente funcional, la próxima semana se reemplaza con usuarios de BD */
    @Bean
    public UserDetailsService users() {
        UserDetails admin1 = User.builder()
                .username("AdminY")
                .password("{noop}123")
                .roles("USER", "ADMIN")
                .build();

        UserDetails admin2 = User.builder()
                .username("AdminB")
                .password("{noop}456")
                .roles("USER", "ADMIN")
                .build();

        UserDetails admin3 = User.builder()
                .username("AdminT")
                .password("{noop}789")
                .roles("USER", "ADMIN")
                .build();

        UserDetails admin4 = User.builder()
                .username("AdminJ")
                .password("{noop}abc")
                .roles("USER", "ADMIN")
                .build();

        UserDetails user1 = User.builder()
                .username("pedro")
                .password("{noop}111")
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("maria")
                .password("{noop}222")
                .roles("USER")
                .build();

        UserDetails user3 = User.builder()
                .username("ana")
                .password("{noop}333")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin1, admin2, admin3, admin4, user1, user2, user3);
    }

}

package com.hogar;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}

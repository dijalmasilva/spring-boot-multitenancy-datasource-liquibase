package br.com.dijalmasilva.springbootmultitenancyliquibase._application.tenant;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 10/05/2019 20:36
 **/
@Configuration
public class TenantConfiguration {

    @Bean
    public FilterRegistrationBean dawsonApiFilter(){
        FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TenantFilter());
        return registration;
    }
}

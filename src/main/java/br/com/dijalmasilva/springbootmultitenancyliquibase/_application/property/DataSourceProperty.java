package br.com.dijalmasilva.springbootmultitenancyliquibase._application.property;

import lombok.Data;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 10/05/2019 19:19
 **/
@Data
public class DataSourceProperty {

    private String tenantId;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private LiquibaseProperties liquibase;
}
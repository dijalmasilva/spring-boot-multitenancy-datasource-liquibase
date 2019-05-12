package br.com.dijalmasilva.springbootmultitenancyliquibase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(
        exclude = {LiquibaseAutoConfiguration.class }
)
public class SpringbootMultiTenancyLiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultiTenancyLiquibaseApplication.class, args);
    }

}

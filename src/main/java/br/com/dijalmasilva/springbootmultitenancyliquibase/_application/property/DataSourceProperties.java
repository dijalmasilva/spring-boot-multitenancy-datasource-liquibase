package br.com.dijalmasilva.springbootmultitenancyliquibase._application.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 10/05/2019 19:18
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring")
public class DataSourceProperties {

    private List<DataSourceProperty> dataSources = new ArrayList<>();
}

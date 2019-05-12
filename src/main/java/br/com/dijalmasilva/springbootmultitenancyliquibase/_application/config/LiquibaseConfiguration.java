package br.com.dijalmasilva.springbootmultitenancyliquibase._application.config;

import br.com.dijalmasilva.springbootmultitenancyliquibase._application.bean.MultiTenantDataSourceSpringLiquibase;
import br.com.dijalmasilva.springbootmultitenancyliquibase._application.property.DataSourceProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 11/05/2019 04:34
 **/
@Configuration
@ConditionalOnProperty(prefix = "spring.liquibase", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(LiquibaseProperties.class)
@AllArgsConstructor
public class LiquibaseConfiguration {

    private LiquibaseProperties properties;
    private DataSourceProperties dataSourceProperties;

    @Bean
    @DependsOn("tenantRoutingDataSource")
    public MultiTenantDataSourceSpringLiquibase liquibaseMultiTenancy(Map<Object, Object> dataSources,
                                                                      @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        // to run changeSets of the liquibase asynchronous
        MultiTenantDataSourceSpringLiquibase liquibase = new MultiTenantDataSourceSpringLiquibase(taskExecutor);
        dataSources.forEach((tenant, dataSource) -> liquibase.addDataSource((String) tenant, (DataSource) dataSource));
        dataSourceProperties.getDataSources().forEach(dbProperty -> {
            if (dbProperty.getLiquibase() != null) {
                liquibase.addLiquibaseProperties(dbProperty.getTenantId(), dbProperty.getLiquibase());
            }
        });

        liquibase.setContexts(properties.getContexts());
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        return liquibase;
    }

}

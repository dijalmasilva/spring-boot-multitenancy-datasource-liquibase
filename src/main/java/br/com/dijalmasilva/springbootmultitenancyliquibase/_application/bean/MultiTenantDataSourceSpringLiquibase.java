package br.com.dijalmasilva.springbootmultitenancyliquibase._application.bean;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 11/05/2019 17:42
 **/
@Data
@Slf4j
public class MultiTenantDataSourceSpringLiquibase implements InitializingBean, ResourceLoaderAware {

    public static final String DISABLED_MESSAGE = "Liquibase is disabled";
    public static final String STARTING_ASYNC_MESSAGE =
            "Starting Liquibase asynchronously, your database might not be ready at startup!";
    public static final String STARTING_SYNC_MESSAGE = "Starting Liquibase synchronously";
    public static final String STARTED_MESSAGE = "Liquibase has updated your database in {} ms";
    public static final String EXCEPTION_MESSAGE = "Liquibase could not start correctly, your database is NOT ready: " +
            "{}";

    public static final long SLOWNESS_THRESHOLD = 5; // seconds
    public static final String SLOWNESS_MESSAGE = "Warning, Liquibase took more than {} seconds to start up!";

    private final Map<String, DataSource> dataSources = new HashMap<>();
    private final Map<String, LiquibaseProperties> propertiesDataSources = new HashMap<>();

    private ResourceLoader resourceLoader;

    private String changeLog;

    private String contexts;

    private String labels;

    private Map<String, String> parameters;

    private String defaultSchema;

    private String liquibaseSchema;

    private String liquibaseTablespace;

    private String databaseChangeLogTable;

    private String databaseChangeLogLockTable;

    private boolean dropFirst;

    private boolean shouldRun = true;

    private File rollbackFile;

    private final TaskExecutor taskExecutor;

    public MultiTenantDataSourceSpringLiquibase(@Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public MultiTenantDataSourceSpringLiquibase() {
        taskExecutor = null;
    }

    public void addDataSource(String tenant, DataSource dataSource) {
        this.dataSources.put(tenant, dataSource);
    }

    public void addLiquibaseProperties(String tenant, LiquibaseProperties properties) {
        this.propertiesDataSources.put(tenant, properties);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("DataSources based multiTenancy enabled");
        runOnAllDataSources();
    }

    private void runOnAllDataSources() throws LiquibaseException {
        dataSources.forEach((tenant, dataSource) -> {
            log.info("Initializing Liquibase for data source " + tenant);
            final LiquibaseProperties lProperty = propertiesDataSources.get(tenant);
            SpringLiquibase liquibase = lProperty != null ? getSpringLiquibase(dataSource, lProperty) : getSpringLiquibase(dataSource);
            if (taskExecutor != null) {
                taskExecutor.execute(() -> {
                    try {
                        log.warn(STARTING_ASYNC_MESSAGE);
                        initDb(liquibase);
                    } catch (LiquibaseException e) {
                        log.error(EXCEPTION_MESSAGE, e.getMessage(), e);
                    }
                });
            } else {
                try {
                    log.warn(STARTING_ASYNC_MESSAGE);
                    initDb(liquibase);
                } catch (LiquibaseException e) {
                    log.error(EXCEPTION_MESSAGE, e.getMessage(), e);
                }
            }

            log.info("Liquibase ran for data source " + tenant);
        });
    }

    private void initDb(SpringLiquibase liquibase) throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        liquibase.afterPropertiesSet();
        watch.stop();
        log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
        if (watch.getTotalTimeMillis() > SLOWNESS_THRESHOLD * 1000L) {
            log.warn(SLOWNESS_MESSAGE, SLOWNESS_THRESHOLD);
        }
    }

    private SpringLiquibase getSpringLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLog);
        liquibase.setChangeLogParameters(parameters);
        liquibase.setContexts(contexts);
        liquibase.setLabels(labels);
        liquibase.setDropFirst(dropFirst);
        liquibase.setShouldRun(shouldRun);
        liquibase.setRollbackFile(rollbackFile);
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(defaultSchema);
        liquibase.setLiquibaseSchema(liquibaseSchema);
        liquibase.setLiquibaseTablespace(liquibaseTablespace);
        liquibase.setDatabaseChangeLogTable(databaseChangeLogTable);
        liquibase.setDatabaseChangeLogLockTable(databaseChangeLogLockTable);
        return liquibase;
    }

    private SpringLiquibase getSpringLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setContexts(properties.getContexts());
        liquibase.setLabels(properties.getLabels());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setRollbackFile(properties.getRollbackFile());
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setLiquibaseSchema(properties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(properties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
        return liquibase;
    }
}

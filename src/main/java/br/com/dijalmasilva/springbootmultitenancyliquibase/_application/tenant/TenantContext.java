package br.com.dijalmasilva.springbootmultitenancyliquibase._application.tenant;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 10/05/2019 00:50
 **/
public class TenantContext {

    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    static String getCurrentTenant() {
        return currentTenant.get();
    }

    static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    static void clear() {
        currentTenant.remove();
    }
}

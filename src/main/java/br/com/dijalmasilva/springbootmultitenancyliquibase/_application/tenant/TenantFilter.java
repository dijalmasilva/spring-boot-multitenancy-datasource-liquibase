package br.com.dijalmasilva.springbootmultitenancyliquibase._application.tenant;

import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 10/05/2019 20:28
 **/
public class TenantFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final String X_TENANT_ID = "X-TenantID";

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String tenantId = httpServletRequest.getHeader(X_TENANT_ID);

        if (tenantId == null) {
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\": \"No tenant header supplied\"}");
            response.getWriter().flush();
            TenantContext.clear();
            return;
        }

        TenantContext.setCurrentTenant(tenantId);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

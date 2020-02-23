package net.medrag.account_service.filter;

import net.medrag.account_service.service.api.MetricsMode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Intercepts every request and increases request counter, basing on picked metrics implementation
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Component
public class MetricFilter extends OncePerRequestFilter {

    private MetricsMode metricsService;

    /**
     * @param metricsService is OK, app can always find only one bean because of @Conditional
     */
    public MetricFilter(MetricsMode metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.matches("^/amount/get/\\d+")) {
            metricsService.increaseGetAmountCount();
        } else if (requestURI.matches("^/amount/add/\\d+/.+")) {
            metricsService.increaseAddAmountCount();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

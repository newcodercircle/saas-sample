package com.github.newcodercircle.saassample.filter;

import com.github.newcodercircle.saassample.properties.TenantProperties;
import com.github.newcodercircle.saassample.util.DomainTenantParseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 租户解析过滤器
 *
 * @author xiaobai
 * @since 2022-11-28
 */
@Slf4j
@RequiredArgsConstructor
public class TenantParseFilter extends OncePerRequestFilter {

    private final TenantProperties tenantProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestServerName = request.getServerName();
        String tenant = DomainTenantParseUtils.parseDomainTenant(requestServerName, tenantProperties.getRootDomain());
        if (!Objects.isNull(tenant)) {
            // 获取到了租户标识
            log.info("域名解析到的租户标识：[{}]", tenant);
            // TODO 写自己的业务操作，比如设置租户上下文传递
        }
        filterChain.doFilter(request, response);
    }

}

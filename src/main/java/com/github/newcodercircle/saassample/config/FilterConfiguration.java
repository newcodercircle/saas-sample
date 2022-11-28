package com.github.newcodercircle.saassample.config;

import com.github.newcodercircle.saassample.filter.TenantParseFilter;
import com.github.newcodercircle.saassample.properties.TenantProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 过滤器配置
 *
 * @author xiaobai
 * @since 2022-11-28
 */
@Configuration
@EnableConfigurationProperties({TenantProperties.class})
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<TenantParseFilter> tenantParseFilter(TenantProperties tenantProperties) {
        FilterRegistrationBean<TenantParseFilter> registrationBean = new FilterRegistrationBean<>();
        TenantParseFilter tenantParseFilter = new TenantParseFilter(tenantProperties);
        registrationBean.setFilter(tenantParseFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

}

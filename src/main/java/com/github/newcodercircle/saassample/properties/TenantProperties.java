package com.github.newcodercircle.saassample.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 租户配置
 *
 * @author xiaobai
 * @since 2022-11-28
 */
@ConfigurationProperties(prefix = "xiaobai.tenant")
@Data
public class TenantProperties {
    /**
     * 租户根域名
     */
    private String rootDomain;

}

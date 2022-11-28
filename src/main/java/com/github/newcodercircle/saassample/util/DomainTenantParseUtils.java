package com.github.newcodercircle.saassample.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.xbill.DNS.*;

import java.net.UnknownHostException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 域名解析租户
 *
 * @author xaiobai
 * @since 2022-11-28
 */
public class DomainTenantParseUtils {

    // DNS缓存
    private static final org.xbill.DNS.Cache CACHE = new org.xbill.DNS.Cache();

    /**
     * 解析域名租户
     *
     * @param domain           当前访问域名
     * @param tenantRootDomain 租户的根域名（可以是二级或者三级域名）
     * @return 租户标识
     */
    @Nullable
    public static String parseDomainTenant(@NonNull String domain, @NonNull String tenantRootDomain) {
        // ip不解析
        if (IpUtils.isIP(domain)) {
            return null;
        }
        String resolvingResult;
        // 当前访问域名在租户根域名下解析结果为当前访问域名
        if (domain.endsWith(tenantRootDomain)) {
            resolvingResult = domain;
        } else {
            resolvingResult = getResolvingResult(domain, Type.CNAME);
        }
        // 解析结果为空直接返回
        if (Objects.isNull(resolvingResult)) {
            return null;
        }
        // 解析结果不在租户根域名下
        if (!resolvingResult.endsWith(tenantRootDomain)) {
            return null;
        }
        String[] rootDomainArray = tenantRootDomain.split("\\.");
        String[] resolvingResultArray = resolvingResult.split("\\.");
        // 取租户根域名的下一级
        if (resolvingResultArray.length > rootDomainArray.length) {
            return resolvingResultArray[resolvingResultArray.length - (rootDomainArray.length + 1)];
        }
        return null;
    }

    /**
     * 获取域名解析结果
     *
     * @param domain 域名
     * @param type   类型 A记录 或者 CNAME
     * @see Type
     */
    @Nullable
    public static String getResolvingResult(String domain, int type) {
        Resolver resolver;
        Lookup lookup;
        try {
            // 设置DNS服务器，这里使用常用的114.114.114.114
            // TODO 这里也可做成配置化
            resolver = new SimpleResolver("114.114.114.114");
            lookup = new Lookup(domain, type);
        } catch (TextParseException | UnknownHostException e) {
            throw new RuntimeException("域名解析异常");
        }
        lookup.setResolver(resolver);
        lookup.setCache(CACHE);
        lookup.run();
        if (lookup.getResult() == Lookup.SUCCESSFUL) {
            String[] results = CACHE.toString().split("\\n");
            for (String result : results) {
                String rule = "(?<=\\[)(.+?)(?=.])";
                Pattern pattern = Pattern.compile(rule);
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
